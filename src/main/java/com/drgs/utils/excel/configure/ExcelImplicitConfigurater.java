package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelImplicit;
import com.drgs.utils.excel.strategy.RangeParseStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author wangyao
 */
@Slf4j
public class ExcelImplicitConfigurater implements Configurater {

    @Override
    public <T> void configure(T entity, Field field, Object[] row) throws Exception{
        field.setAccessible(true);
        ExcelImplicit excelImplicit = field.getAnnotation(ExcelImplicit.class);
        Class<? extends RangeParseStrategy> parseStrategy = excelImplicit.parseStrategy();
        RangeParseStrategy rangeParseStrategy = parseStrategy.newInstance();
        Object parse = rangeParseStrategy.parse(row);
        field.set(entity, parse);
    }
}
