package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Function;

/**
 * @author wangyao
 */
public class ValidatorManager {

    private static final Map<String, Function> VALIDATORS = Maps.newHashMap();

    static {
        VALIDATORS.put(String.class.getName(), new StringValidator());
        VALIDATORS.put(Integer.class.getName(), new IntegerAngLongValidator());
        VALIDATORS.put(int.class.getName(), new IntegerAngLongValidator());
        VALIDATORS.put(Long.class.getName(), new IntegerAngLongValidator());
        VALIDATORS.put(String.class.getName(), new BooleanValidator());
    }
}
