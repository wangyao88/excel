package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author wangyao
 */
public class BooleanValidator implements Function {

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
