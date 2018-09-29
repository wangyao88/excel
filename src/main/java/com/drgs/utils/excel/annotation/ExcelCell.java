package com.drgs.utils.excel.annotation;

import java.lang.annotation.*;

/**
 * 用于javaBean field为基本数据类型  标注对应excel 列坐标及值类型
 * @author wangyao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCell {

    int position();

    boolean nullable() default true;
}
