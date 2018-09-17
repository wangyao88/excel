package com.drgs.utils.excel.configure;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author wangyao
 */
public interface Configurater {

    <T> void configure(T entity, Field field, Object[] row);

    static <T> T configurateValue(String cellTypeName, String value) {
        if(cellTypeName.equals(String.class.getName())) {
            return (T)String.valueOf(value);
        }
        if(cellTypeName.equals(Integer.class.getName()) || cellTypeName.equals(int.class.getName())) {
            if (value.contains(".")) {
                value = value.substring(0, value.lastIndexOf("."));
            }
            return (T)Integer.valueOf(value);
        }
        if(cellTypeName.equals(Long.class.getName()) || cellTypeName.equals(long.class.getName())) {
            if (value.contains(".")) {
                value = value.substring(0, value.lastIndexOf("."));
            }
            return (T)Long.valueOf(value);
        }
        if(cellTypeName.equals(Double.class.getName()) || cellTypeName.equals(double.class.getName())) {
            return (T)Double.valueOf(value);
        }
        if(cellTypeName.equals(Float.class.getName()) || cellTypeName.equals(float.class.getName())) {
            return (T)Float.valueOf(value);
        }
        if(cellTypeName.equals(Boolean.class.getName()) || cellTypeName.equals(boolean.class.getName())) {
            if(StringUtils.isEmpty(value)){
                return (T)Boolean.FALSE;
            }
            if("true".equals(value.toLowerCase().trim()) || "1.0".equals(value.trim())){
                return (T)Boolean.TRUE;
            }
        }
        return null;
    }
}
