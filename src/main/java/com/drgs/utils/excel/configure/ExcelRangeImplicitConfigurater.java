package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelRangeImplicit;
import com.drgs.utils.excel.strategy.RangeParseStrategy;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * @author wangyao
 */
@Slf4j
public class ExcelRangeImplicitConfigurater implements Configurater {

    @Override
    public <T> void configure(T entity, Field field, Object[] row) throws Exception{
        field.setAccessible(true);
        ExcelRangeImplicit excelImplicit = field.getAnnotation(ExcelRangeImplicit.class);
        Class<? extends RangeParseStrategy> parseStrategy = excelImplicit.parseStrategy();
        RangeParseStrategy rangeParseStrategy = parseStrategy.newInstance();
        Object parse = rangeParseStrategy.parse(row);
        field.set(entity, parse);
    }
}
