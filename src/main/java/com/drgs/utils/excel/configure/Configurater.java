package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.bean.ValidateResult;
import com.drgs.utils.excel.exception.ValidateException;
import com.drgs.utils.excel.function.FunctionManager;

import java.lang.reflect.Field;

/**
 * @author wangyao
 */
public interface Configurater {

    <T> void configure(T entity, Field field, Object[] row) throws Exception;

    static <T> T configurateValue(String cellTypeName, String value) throws Exception{
        ValidateResult validateResult = FunctionManager.getInstance().doValidate(cellTypeName, value);
        if(validateResult.isResult()) {
            return (T)validateResult.getData();
        }
        throw new ValidateException(validateResult.getMsg());
    }
}
