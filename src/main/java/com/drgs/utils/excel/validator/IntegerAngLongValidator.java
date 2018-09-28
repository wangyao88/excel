package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;

import java.util.regex.Pattern;

/**
 * @author wangyao
 */
public class IntegerAngLongValidator implements Validator {

    private static final Pattern PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    @Override
    public ValidateResult validate(Object value) {
        if(isIntegerOrLong(value+"")) {
            return ValidateResult.buildSuccessValidateResult();
        }
        return ValidateResult.buildFailureValidateResult();
    }

    public static IntegerAngLongValidator getInstance() {
        return new IntegerAngLongValidator();
    }

    private static boolean isIntegerOrLong(String value) {
        if("-".equals(value) || "+".equals(value)){
            return false;
        }
        return PATTERN.matcher(value).matches();
    }
}
