package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;

import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author wangyao
 */
public class FloatValidator implements Function {

    private static final Pattern PATTERN = Pattern.compile("^[-\\+]?[.\\d]*$");

    @Override
    public ValidateResult apply(Object value) {
        if(Objects.isNull(value)) {
            return ValidateResult.buildSuccessValidateResult("空对象，无需校验",null);
        }
        String str = value+"";
        if(isFloat(str)) {
            return ValidateResult.buildSuccessValidateResult("浮点型转换成功", Float.valueOf(str));
        }
        return ValidateResult.buildFailureValidateResult("值非法，无法转化为浮点型");
    }

    private static boolean isFloat(String value) {
        if("-".equals(value) || "+".equals(value)){
            return false;
        }
        return PATTERN.matcher(value).matches();
    }
}
