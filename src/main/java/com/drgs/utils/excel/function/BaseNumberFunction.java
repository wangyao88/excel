package com.drgs.utils.excel.function;

import com.drgs.utils.excel.bean.ValidateResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author wangyao
 */
public abstract class BaseNumberFunction implements Function {

    private static final Pattern INTEGER_LONG_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");
    private static final Pattern FLOAT_DOUBLE_PATTERN = Pattern.compile("^[-\\+]?[.\\d]*$");

    private Function function;
    private Object nullData;

    public BaseNumberFunction(Function function, Object nullData) {
        this.function = function;
        this.nullData = nullData;
    }

    @Override
    public ValidateResult apply(Object value) {
        String str = value+"";
        if(Objects.isNull(value) || StringUtils.isEmpty(str)) {
            return ValidateResult.buildSuccessValidateResult("空对象，无需校验",nullData);
        }
        if(isNeedSubString()){
            str = getSubString(str);
        }
        if(isValida(str)) {
            return ValidateResult.buildSuccessValidateResult("转换成功", function.apply(str));
        }
        return ValidateResult.buildFailureValidateResult("值非法，无法转化");
    }

    private String getSubString(String str) {
        if (str.contains(".")) {
            str = str.substring(0, str.lastIndexOf("."));
        }
        return str;
    }

    protected abstract boolean isNeedSubString();

    protected abstract boolean isValida(String value);

    protected boolean isIntegerOrLong(String value) {
        if("-".equals(value) || "+".equals(value)){
            return false;
        }
        return INTEGER_LONG_PATTERN.matcher(value).matches();
    }

    protected boolean isFloatOrDouble(String value) {
        if("-".equals(value) || "+".equals(value)){
            return false;
        }
        return FLOAT_DOUBLE_PATTERN.matcher(value).matches();
    }
}
