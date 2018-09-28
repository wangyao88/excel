package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelCell;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author wangyao
 */
@Slf4j
public class ExcelCellConfigurater implements Configurater {

    @Override
    public <T> void configure(T entity, Field field, Object[] row) throws Exception {
        field.setAccessible(true);
        ExcelCell excelCell = field.getAnnotation(ExcelCell.class);
        int position = excelCell.position();
        Object value = StringUtils.EMPTY;
        int arrLength = row.length;
        if(position < arrLength){
            value = row[position];
        }
        Type genericType = field.getGenericType();
        field.set(entity, Configurater.configurateValue(genericType.getTypeName(), value+StringUtils.EMPTY));
    }
}
