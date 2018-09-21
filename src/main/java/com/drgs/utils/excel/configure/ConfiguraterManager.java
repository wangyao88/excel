package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelSingleCellImplicit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author wangyao
 */
public class ConfiguraterManager {

    public static <T> void configurate (T entity, Field field, Object[] row){
        Map<Class<? extends Annotation>, Configurater> configuraters = ConfiguraterFactory.getInstance().getConfiguraters();
        configuraters.forEach((key,value) -> {
            if (field.isAnnotationPresent(key)){
                value.configure(entity,field,row);
            }
        });
    }
}
