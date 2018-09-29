package com.drgs.utils.excel.function;

import com.drgs.utils.excel.bean.ValidateResult;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author wangyao
 */
public class BooleanFunction implements Function {

    @Override
    public ValidateResult apply(Object value) {
        Boolean flag = Boolean.FALSE;
        if(!Objects.isNull(value)) {
            String str = value+"";
            if("true".equals(str.toLowerCase().trim())){
                flag = Boolean.TRUE;
            }
        }
        return ValidateResult.buildSuccessValidateResult("",flag);
    }
}
