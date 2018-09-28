package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelSingleCellImplicit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author wangyao
 */
@Slf4j
public class ExcelSingleCellImplicitConfigurater implements Configurater {

    @Override
    public <T> void configure(T entity, Field field, Object[] row) throws Exception{
        field.setAccessible(true);
        ExcelSingleCellImplicit excelSingleCellImplicit = field.getAnnotation(ExcelSingleCellImplicit.class);
        Class<?> cellType = excelSingleCellImplicit.type();
        int position = excelSingleCellImplicit.position();
        Object value = null;
        int arrLength = row.length;
        if(position < arrLength){
            value = row[position];
        }
        field.set(entity, configurateBeanValue(cellType, value+StringUtils.EMPTY));
    }

    private static <T> T configurateBeanValue(Class<T> cellType, String value) throws Exception {
        T obj = cellType.newInstance();
        Field[] fields = cellType.getDeclaredFields();
        Field firstField = fields[0];
        Type genericType = firstField.getGenericType();
        Class<?> declaringClass = firstField.getDeclaringClass();
        firstField.setAccessible(true);
        firstField.set(obj,Configurater.configurateValue(genericType.getTypeName(),value));
        return obj;
    }
}
