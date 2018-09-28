package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author wangyao
 */
public class StringValidator implements Function {

    @Override
    public ValidateResult apply(Object value) {
        return ValidateResult.buildSuccessValidateResult("",value);
    }
}
