package com.drgs.utils.excel.configure;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author wangyao
 */
public class ConfiguraterManager {

    public static <T> void configurate (T entity, Field field, Object[] row) throws Exception{
        Map<Class<? extends Annotation>, Configurater> configuraters = ConfiguraterFactory.getInstance().getConfiguraters();
        for(Map.Entry<Class<? extends Annotation>, Configurater> entry : configuraters.entrySet()) {
            if (field.isAnnotationPresent(entry.getKey())){
                entry.getValue().configure(entity,field,row);
            }
        }
    }
}
