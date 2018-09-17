package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelDateCell;
import com.drgs.utils.excel.strategy.DateParseStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author wangyao
 */
@Slf4j
public class ExcelDateCellConfigurater implements Configurater {

    @Override
    public <T> void configure(T entity, Field field, Object[] row) {
        try {
            field.setAccessible(true);
            ExcelDateCell excelDateCell = field.getAnnotation(ExcelDateCell.class);
            int position = excelDateCell.position();
            Object value = null;
            int arrLength = row.length;
            if(position < arrLength){
                value = row[position];
            }
            DateParseStrategy dateParseStrategy = new DateParseStrategy();
            field.set(entity, dateParseStrategy.parse(value));
        } catch (Exception e) {
            log.error("设置属性值失败",e);
        }
    }
}
