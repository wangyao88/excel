package com.drgs.utils.excel.convert;

import com.drgs.utils.excel.bean.Bean;
import com.drgs.utils.excel.bean.Coordinate;
import com.drgs.utils.excel.bean.Point;
import com.drgs.utils.excel.bean.Result;
import com.drgs.utils.excel.configure.ConfiguraterManager;
import com.drgs.utils.excel.exception.FileNotExistsException;
import com.drgs.utils.excel.exception.NotExcelFileException;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import lombok.Cleanup;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * @author wangyao
 */
@Slf4j
@Data
public class ExcelToBeanConverter {

    private List<Coordinate> coordinates = Lists.newArrayList();

    public static ExcelToBeanConverter getInstance() {
        ExcelToBeanConverter excelToBeanConverter = new ExcelToBeanConverter();
        Coordinate coordinate = new Coordinate();
        coordinate.setSheetIndex(0);
        coordinate.setStartRowIndex(0);
        List<Coordinate> coordinates = Lists.newArrayList();
        coordinates.add(coordinate);
        excelToBeanConverter.setCoordinates(coordinates);
        return excelToBeanConverter;
    }

    public ExcelToBeanConverter setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public <T> Result<T> getBeans(String path, Class<T> clazz) {
        File file = new File(path);
        return getBeans(file, clazz);
    }

    public <T> Result<T> getBeans(byte[] bytes, String fileName, Class<T> clazz) {
        File file = null;
        try {
            file = byte2File(bytes,fileName);
        } catch (IOException e) {
            log.error("byte2File失败！",e);
            return new Result<T>();
        }
        return getBeans(file, clazz);
    }

    public <T> Result<T> getBeans(File file, Class<T> clazz) {
        Optional<Workbook> workbookOptional = Optional.empty();
        try {
            workbookOptional = Excel.getWorkbook(file);
        } catch (FileNotExistsException e) {
            log.error(e.getMessage(),e);
        } catch (NotExcelFileException e) {
            log.error(e.getMessage(),e);
        }
        Result<T> result = new Result<T>();
        if(workbookOptional.isPresent()) {
            Workbook workbook = workbookOptional.get();
            this.getCoordinates().forEach(coordinate -> {
                convertCoordinate(clazz, result, workbook, coordinate);
            });
        }
        return result;
    }

    private <T> void convertCoordinate(Class<T> clazz, Result<T> result, Workbook workbook, Coordinate coordinate) {
        int sheetIndex = coordinate.getSheetIndex();
        int numberOfSheets = workbook.getNumberOfSheets();
        if(sheetIndex >= numberOfSheets){
            log.error("sheetIndex越界");
            return;
        }
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        int lastRowNum = Objects.isNull(coordinate.getEndRowIndex()) ?
                sheet.getLastRowNum() :
                Math.min(coordinate.getEndRowIndex(),sheet.getLastRowNum());
        for (int i = coordinate.getStartRowIndex(); i <= lastRowNum; i++) {
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

    private File byte2File(byte[] bytes, String fileName) throws IOException {
        File file = File.createTempFile(Files.getNameWithoutExtension(fileName),Files.getFileExtension(fileName));
        @Cleanup
        OutputStream output = new FileOutputStream(file);
        @Cleanup
        BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
        bufferedOutput.write(bytes);
        return file;
    }
    private <T> T parseBean(Class<T> clazz, Sheet sheet, int i) throws Exception {
        Object[] arr = convertArrayByRow(sheet.getRow(i));
        return convertBeanFromArray(arr, clazz);
    }



    private Object[] convertArrayByRow(Row row) throws Exception{
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

    private <T> T convertBeanFromArray(Object[] arr, Class<T> clazz) throws Exception{
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

        List<Coordinate> coordinates = Lists.newArrayList();
        Coordinate coordinate1 = new Coordinate();
        coordinate1.setSheetIndex(0);
        coordinate1.setStartRowIndex(0);
        coordinates.add(coordinate1);
        Coordinate coordinate2 = new Coordinate();
        coordinate2.setSheetIndex(1);
        coordinate2.setStartRowIndex(0);
        coordinates.add(coordinate2);

        Result<Bean> beans = ExcelToBeanConverter.getInstance().setCoordinates(coordinates).getBeans(path, Bean.class);
        System.out.println(beans);
    }

}