package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.bean.ValidateResult;
import com.drgs.utils.excel.exception.ValidateException;
import com.drgs.utils.excel.validator.FloatAndDoubleValidator;
import com.drgs.utils.excel.validator.IntegerAngLongValidator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author wangyao
 */
public interface Configurater {

    <T> void configure(T entity, Field field, Object[] row) throws Exception;

    static <T> T configurateValue(String cellTypeName, String value) throws Exception{
        if(cellTypeName.equals(String.class.getName())) {
            return (T)String.valueOf(value);
        }
        if(cellTypeName.equals(Integer.class.getName()) || cellTypeName.equals(int.class.getName())) {
            if (value.contains(".")) {
                value = value.substring(0, value.lastIndexOf("."));
            }
            ValidateResult validateResult = IntegerAngLongValidator.getInstance().validate(value);
            if(validateResult.isResult()){
                return (T)Integer.valueOf(value);
            }
            throw new ValidateException("值非法，无法转化为整型");
        }
        if(cellTypeName.equals(Long.class.getName()) || cellTypeName.equals(long.class.getName())) {
            if (value.contains(".")) {
                value = value.substring(0, value.lastIndexOf("."));
            }
            ValidateResult validateResult = IntegerAngLongValidator.getInstance().validate(value);
            if(validateResult.isResult()){
                return (T)Long.valueOf(value);
            }
            throw new ValidateException("值非法，无法转化为长整型");
        }
        if(cellTypeName.equals(Double.class.getName()) || cellTypeName.equals(double.class.getName())) {
            ValidateResult validateResult = FloatAndDoubleValidator.getInstance().validate(value);
            if(validateResult.isResult()){
                return (T)Double.valueOf(value);
            }
            throw new ValidateException("值非法，无法转化为浮点型");
        }
        if(cellTypeName.equals(Float.class.getName()) || cellTypeName.equals(float.class.getName())) {
            ValidateResult validateResult = FloatAndDoubleValidator.getInstance().validate(value);
            if(validateResult.isResult()){
                return (T)Float.valueOf(value);
            }
            throw new ValidateException("值非法，无法转化为浮点型");
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
