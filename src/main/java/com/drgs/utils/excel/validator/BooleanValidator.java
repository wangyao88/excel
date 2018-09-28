package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;

import java.util.function.Function;

/**
 * @author wangyao
 */
public class BooleanValidator implements Function {

    @Override
    public ValidateResult apply(Object value) {
        return ValidateResult.buildSuccessValidateResult("",value);
    }
}
