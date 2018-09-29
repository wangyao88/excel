package com.drgs.utils.excel.annotation;

import com.drgs.utils.excel.strategy.ParseStrategy;

import java.lang.annotation.*;
import java.util.Date;

/**
 * 用于javaBean field为基本数据类型  标注对应excel 列坐标及值类型
 * @author wangyao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelDateCell {

    int position();

    Class<?> type() default Date.class;

    String partten() default "yyyy-MM-dd";

    boolean nullable() default true;
}
