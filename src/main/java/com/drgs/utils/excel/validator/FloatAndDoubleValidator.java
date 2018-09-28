package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;

import java.util.regex.Pattern;

/**
 * @author wangyao
 */
public class FloatAndDoubleValidator implements Validator {

    private static final Pattern PATTERN = Pattern.compile("^[-\\+]?[.\\d]*$");

    @Override
    public ValidateResult validate(Object value) {
        if(isFloatOrDouble(value+"")) {
            return ValidateResult.buildSuccessValidateResult();
        }
        return ValidateResult.buildFailureValidateResult();
    }

    public static FloatAndDoubleValidator getInstance() {
        return new FloatAndDoubleValidator();
    }

    private static boolean isFloatOrDouble(String value) {
        if("-".equals(value) || "+".equals(value)){
            return false;
        }
        return PATTERN.matcher(value).matches();
    }
}
