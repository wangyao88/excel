package com.drgs.utils.excel.convert;

import com.drgs.utils.excel.annotation.ExcelCell;
import com.drgs.utils.excel.annotation.ExcelDateCell;
import com.drgs.utils.excel.annotation.ExcelImplicit;
import com.drgs.utils.excel.annotation.ExcelSingleCellImplicit;
import com.drgs.utils.excel.bean.Bean;
import com.drgs.utils.excel.exception.FileNotExistsException;
import com.drgs.utils.excel.exception.NotExcelFileException;
import com.drgs.utils.excel.strategy.DateParseStrategy;
import com.drgs.utils.excel.strategy.ParseStrategy;
import com.drgs.utils.excel.strategy.RangeParseStrategy;
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
import java.lang.reflect.Type;
import java.util.*;

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
                if (field.isAnnotationPresent(ExcelCell.class)) {
                    field.setAccessible(true);
                    ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
                    int position = excelCell.position();
                    Object value = StringUtils.EMPTY;
                    if(position < arrLength){
                        value = arr[position];
                    }
                    Type genericType = field.getGenericType();
                    field.set(entity, configurateValue(genericType.getTypeName(), value+StringUtils.EMPTY));
                    continue;
                }
                if (field.isAnnotationPresent(ExcelDateCell.class)) {
                    field.setAccessible(true);
                    ExcelDateCell excelDateCell = field.getAnnotation(ExcelDateCell.class);
                    int position = excelDateCell.position();
                    Object value = null;
                    if(position < arrLength){
                        value = arr[position];
                    }
                    DateParseStrategy dateParseStrategy = new DateParseStrategy();
                    field.set(entity, dateParseStrategy.parse(value));
                    continue;
                }
                if (field.isAnnotationPresent(ExcelSingleCellImplicit.class)) {
                    field.setAccessible(true);
                    ExcelSingleCellImplicit excelSingleCellImplicit = field.getAnnotation(ExcelSingleCellImplicit.class);
                    Class<?> cellType = excelSingleCellImplicit.type();
                    int position = excelSingleCellImplicit.position();
                    Object value = null;
                    if(position < arrLength){
                        value = arr[position];
                    }
                    field.set(entity, configurateBeanValue(cellType, value+StringUtils.EMPTY));
                    continue;
                }

                if (field.isAnnotationPresent(ExcelImplicit.class)) {
                    field.setAccessible(true);
                    ExcelImplicit excelImplicit = field.getAnnotation(ExcelImplicit.class);
                    Class<? extends RangeParseStrategy> parseStrategy = excelImplicit.parseStrategy();
                    RangeParseStrategy rangeParseStrategy = parseStrategy.newInstance();
                    Object parse = rangeParseStrategy.parse(arr);
                    field.set(entity, parse);
                }
            }
            return entity;
        } catch (Exception e) {
            log.error("excel值转化失败",e);
        }
        return null;
    }

    private static <T> T configurateBeanValue(Class<T> cellType, String value) {
        T obj = null;
        try {
            obj = cellType.newInstance();
            Field[] fields = cellType.getDeclaredFields();
            Field firstField = fields[0];
            Type genericType = firstField.getGenericType();
            Class<?> declaringClass = firstField.getDeclaringClass();
            firstField.setAccessible(true);
            firstField.set(obj,configurateValue(genericType.getTypeName(),value));
        } catch (Exception e) {
            log.error("转化嵌套实体对象失败",e);
        }
        return obj;
    }

    private static Date configurateDateValue(Object value) {
        return (Date)value;
    }

    private static <T> T configurateValue(Class<T> cellType, String value) {
        Object obj = configurateValue(cellType.getName(), value);
        return Objects.isNull(obj) ? cellType.cast(value) : (T)obj;
    }

    private static <T> T configurateValue(String cellTypeName, String value) {
        if(cellTypeName.equals(String.class.getName())) {
            return (T)String.valueOf(value);
        }
        if(cellTypeName.equals(Integer.class.getName()) || cellTypeName.equals(int.class.getName())) {
            if (value.contains(".")) {
                value = value.substring(0, value.lastIndexOf("."));
            }
            return (T)Integer.valueOf(value);
        }
        if(cellTypeName.equals(Long.class.getName()) || cellTypeName.equals(long.class.getName())) {
            if (value.contains(".")) {
                value = value.substring(0, value.lastIndexOf("."));
            }
            return (T)Long.valueOf(value);
        }
        if(cellTypeName.equals(Double.class.getName()) || cellTypeName.equals(double.class.getName())) {
            return (T)Double.valueOf(value);
        }
        if(cellTypeName.equals(Float.class.getName()) || cellTypeName.equals(float.class.getName())) {
            return (T)Float.valueOf(value);
        }
        if(cellTypeName.equals(Boolean.class.getName()) || cellTypeName.equals(boolean.class.getName())) {
            if(StringUtils.isEmpty(value)){
                return (T)Boolean.FALSE;
            }
            if("true".equals(value.toLowerCase().trim()) || "1.0".equals(value.trim())){
                return (T)Boolean.TRUE;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        String path = "/Users/localadmin/Documents/temp/test.xlsx";
        List<Bean> beans = ExcelConveter.getBeans(path, Bean.class);
        System.out.println(beans);
    }

}
