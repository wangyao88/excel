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
    public <T> void configure(T entity, Field field, Object[] row) {
        try {
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
        } catch (Exception e) {
            log.error("设置属性值失败",e);
        }
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
            firstField.set(obj,Configurater.configurateValue(genericType.getTypeName(),value));
        } catch (Exception e) {
            log.error("转化嵌套实体对象失败",e);
        }
        return obj;
    }
}
