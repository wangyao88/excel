package com.drgs.utils.excel.annotation;

import java.lang.annotation.*;

/**
 * 用于javaBean field为引用类型  单个单元格对应一个对象 单元格值由指定分隔符进行分割
 * @author wangyao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelSpliceCellImplicit {

    String[] delimiter() default {" ","\t",","};

    Class<?> type();
}
