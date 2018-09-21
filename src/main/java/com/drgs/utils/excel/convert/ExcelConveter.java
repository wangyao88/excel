package com.drgs.utils.excel.convert;

import com.drgs.utils.excel.bean.Bean;
import com.drgs.utils.excel.bean.Point;
import com.drgs.utils.excel.bean.Result;
import com.drgs.utils.excel.configure.ConfiguraterManager;
import com.drgs.utils.excel.exception.FileNotExistsException;
import com.drgs.utils.excel.exception.NotExcelFileException;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;

/**
 * excel javaBean转换器
 * @author wangyao
 */
@Slf4j
public class ExcelConveter {

    private static int DEFAULT_SHEET_INDEX = 0;
    private static final Set<String> EXTENSION = Sets.newHashSet(".xls","xlsx");
    private static final int DEFAULT_START_ROW_NUM = 0;

    public static <T> Result<T> getBeans(String path, Class<T> clazz) {
        return getBeans(DEFAULT_SHEET_INDEX,DEFAULT_START_ROW_NUM,path,clazz);
    }

    public static <T> Result<T> getBeans(boolean hasHeader, String path, Class<T> clazz) {
        int rowNum = hasHeader ? 1 : DEFAULT_START_ROW_NUM;
        return getBeans(DEFAULT_SHEET_INDEX,rowNum,path,clazz);
    }

    public static <T> Result<T> getBeans(int sheetIndex, String path, Class<T> clazz) {
        return getBeans(DEFAULT_SHEET_INDEX,path,clazz);
    }

    public static <T> Result<T> getBeans(int sheetIndex, int startRowNum, String path, Class<T> clazz) {
        Optional<Workbook> workbookOptional = Optional.empty();
        try {
            workbookOptional = readFile(path);
        } catch (FileNotExistsException e) {
            log.error(e.getMessage(),e);
        } catch (NotExcelFileException e) {
            log.error(e.getMessage(),e);
        }
        Result<T> result = new Result<T>();
        if(workbookOptional.isPresent()) {
            Workbook workbook = workbookOptional.get();
            Sheet sheet = workbook.getSheetAt(sheetIndex);
            int lastRowNum = sheet.getLastRowNum();
            for (int i = startRowNum; i <= lastRowNum; i++) {
                Point point = Point.getInstance(sheetIndex,i);
                try {
                    T t = parseBean(clazz, sheet, i);
                    result.getSuccessBeans().put(point,t);
                } catch (Exception e) {
                    result.getFailureBeans().put(point,e.getMessage());
                    result.setSuccess(false);
                }
            }
        }
        return result;
    }

    private static <T> T parseBean(Class<T> clazz, Sheet sheet, int i) throws Exception {
        Object[] arr = convertArrayByRow(sheet.getRow(i));
        return convertBeanFromArray(arr, clazz);
    }

    private static Optional<Workbook> readFile(String path) throws FileNotExistsException, NotExcelFileException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotExistsException();
        }
        String extension = Files.getFileExtension(path);
        if (!file.isFile() || !EXTENSION.contains(extension)) {
            throw new NotExcelFileException();
        }
        try {
            return Optional.of(getWorkbook(file,extension));
        }catch (IOException e) {
            log.error("创建HSSFWorkbook失败！",e);
            return Optional.empty();
        }
    }

    private static Workbook getWorkbook(File file, String extension) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        if(".xls".equals(extension)) {
            return new HSSFWorkbook(fileInputStream);
        }
        return new XSSFWorkbook(fileInputStream);
    }

    private static Object[] convertArrayByRow(Row row) throws Exception{
        int cols = row.getLastCellNum();
        Object[] arr = new Object[cols];
        for (int i = 0; i < cols; i++) {
            Cell cell = row.getCell(i);
            if(cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK){
                arr[i] = StringUtils.EMPTY;
                continue;
            }
            if(cell.getCellType() == Cell.CELL_TYPE_STRING){
                arr[i] = cell.getStringCellValue();
                continue;
            }
            if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                if(HSSFDateUtil.isCellDateFormatted(cell)) {
                    arr[i] = cell.getDateCellValue();
                }else {
                    arr[i] = cell.getNumericCellValue();
                }
                continue;
            }
            if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
                arr[i] = cell.getBooleanCellValue();
            }
        }
        return arr;
    }

    private static <T> T convertBeanFromArray(Object[] arr, Class<T> clazz) throws Exception{
        T entity = clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ConfiguraterManager.configurate(entity,field,arr);
        }
        return entity;
    }

    public static void main(String[] args) {
        String path = "/Users/localadmin/Documents/temp/test.xlsx";
//        String path = "F:/cache/test.xlsx";
        Result<Bean> beans = ExcelConveter.getBeans(false, path, Bean.class);
//        Lists.newArrayList().parallelStream()
        System.out.println(beans);
    }
}
