package com.drgs.utils.excel.annotation;

import java.lang.annotation.*;

/**
 * 用于javaBean field为引用类型  单个单元格对应一个对象 默认对第一个属性赋值
 * @author wangyao
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelSingleCellImplicit {

    int position();

    Class<?> type();
}
