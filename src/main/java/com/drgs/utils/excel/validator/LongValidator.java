package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;

import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author wangyao
 */
public class LongValidator implements Function {

    private static final Pattern PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    @Override
    public ValidateResult apply(Object value) {
        if(Objects.isNull(value)) {
            return ValidateResult.buildSuccessValidateResult("空对象，无需校验",null);
        }
        String str = value+"";
        if (str.contains(".")) {
            str = str.substring(0, str.lastIndexOf("."));
        }
        if(isLong(str)) {
            return ValidateResult.buildSuccessValidateResult("长整型转换成功", Integer.valueOf(str));
        }
        return ValidateResult.buildFailureValidateResult("值非法，无法转化为长整型");
    }

    private static boolean isLong(String value) {
        if("-".equals(value) || "+".equals(value)){
            return false;
        }
        return PATTERN.matcher(value).matches();
    }
}
