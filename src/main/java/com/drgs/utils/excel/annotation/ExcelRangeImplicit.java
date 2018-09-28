package com.drgs.utils.excel.annotation;

import com.drgs.utils.excel.strategy.RangeParseStrategy;

import java.lang.annotation.*;

/**
 * 用于javaBean field为引用类型  多个单元格对应一个对象 单元格值由指定分隔符进行分割
 * @author wangyao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelRangeImplicit {

    Class<? extends RangeParseStrategy> parseStrategy();
}
