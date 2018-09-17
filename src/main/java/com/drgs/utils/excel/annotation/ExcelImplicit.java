package com.drgs.utils.excel.annotation;

import com.drgs.utils.excel.strategy.RangeParseStrategy;

import java.lang.annotation.*;

/**
 * 用于javaBean field为引用类型  单个单元格对应一个对象 自定义解析策略
 * @author wangyao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelImplicit {

    Class<? extends RangeParseStrategy> parseStrategy();
}
