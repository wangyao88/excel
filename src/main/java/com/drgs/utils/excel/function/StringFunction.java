package com.drgs.utils.excel.function;

import com.drgs.utils.excel.bean.ValidateResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @author wangyao
 */
public class StringFunction implements Function {

    @Override
    public ValidateResult apply(Object value) {
        return ValidateResult.buildSuccessValidateResult("",Objects.isNull(value) ? StringUtils.EMPTY : String.valueOf(value));
    }
}
