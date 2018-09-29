package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelDateCell;
import com.drgs.utils.excel.exception.ValidateException;
import com.drgs.utils.excel.strategy.DateParseStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Objects;

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
            boolean nullable = excelDateCell.nullable();
            if(!nullable && (Objects.isNull(value ) || StringUtils.isEmpty(value+""))){
                throw new ValidateException("第"+position+"位置值不允许为空");
            }
            DateParseStrategy dateParseStrategy = new DateParseStrategy();
            field.set(entity, dateParseStrategy.parse(value));
        } catch (Exception e) {
            log.error("设置属性值失败",e);
        }
    }
}
