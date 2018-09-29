package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelCell;
import com.drgs.utils.excel.exception.ValidateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Objects;

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
        boolean nullable = excelCell.nullable();
        if(!nullable && (Objects.isNull(value ) || StringUtils.isEmpty(value+""))){
            throw new ValidateException("第"+position+"位置值不允许为空");
        }
        Type genericType = field.getGenericType();
        field.set(entity, Configurater.configurateValue(genericType.getTypeName(), value+StringUtils.EMPTY));
    }
}
