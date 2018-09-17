package com.drgs.utils.excel.convert;

import com.drgs.utils.excel.bean.Bean;
import com.drgs.utils.excel.configure.ConfiguraterManager;
import com.drgs.utils.excel.exception.FileNotExistsException;
import com.drgs.utils.excel.exception.NotExcelFileException;
import com.google.common.collect.Lists;
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
import java.util.List;
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

    public static <T> List<T> getBeans(String path, Class<T> clazz) {
        return getBeans(DEFAULT_SHEET_INDEX,path,clazz);
    }

    public static <T> List<T> getBeans(int sheetIndex, String path, Class<T> clazz) {
        List<T> list = Lists.newArrayList();
        try {
            Optional<Workbook> workbookOptional = readFile(path);
            if(workbookOptional.isPresent()) {
                Workbook workbook = workbookOptional.get();
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                int lastRowNum = sheet.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                    Object[] arr = convertArrayByRow(sheet.getRow(i));
                    T t = convertBeanFromArray(arr, clazz);
                    list.add(t);
                }
            }
        } catch (FileNotExistsException e) {
            log.error(e.getMessage(),e);
        } catch (NotExcelFileException e) {
            log.error(e.getMessage(),e);
        }
        return list;
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

    private static Object[] convertArrayByRow(Row row) {
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
                    arr[i] = cell.getNumericCellValue()+StringUtils.EMPTY;
                }
                continue;
            }
            if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
                arr[i] = cell.getBooleanCellValue()+StringUtils.EMPTY;
            }
        }
        return arr;
    }

    private static <T> T convertBeanFromArray(Object[] arr, Class<T> clazz) {
        T entity;
        try {
            entity = clazz.newInstance();
            int arrLength = arr.length;
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                ConfiguraterManager.configurate(entity,field,arr);
            }
            return entity;
        } catch (Exception e) {
            log.error("excel值转化失败",e);
        }
        return null;
    }

    public static void main(String[] args) {
//        String path = "/Users/localadmin/Documents/temp/test.xlsx";
        String path = "F:/cache/test.xlsx";
        List<Bean> beans = ExcelConveter.getBeans(path, Bean.class);
        System.out.println(beans);
    }
}
