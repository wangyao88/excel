package com.drgs.utils.excel.function;

import com.drgs.utils.excel.bean.ValidateResult;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Function;

/**
 * @author wangyao
 */
public class FunctionManager {

    private static final Map<String, Function> VALIDATORS = Maps.newHashMap();

    private FunctionManager(){
        VALIDATORS.put(String.class.getName(), new StringFunction());
        VALIDATORS.put(Integer.class.getName(), new IntegerAndLongNumberFunction((value)-> Integer.valueOf(String.valueOf(value)),null));
        VALIDATORS.put(int.class.getName(), new IntegerAndLongNumberFunction((value)-> Integer.valueOf(String.valueOf(value)),0));
        VALIDATORS.put(Long.class.getName(), new IntegerAndLongNumberFunction((value)-> Long.valueOf(String.valueOf(value)),null));
        VALIDATORS.put(long.class.getName(), new IntegerAndLongNumberFunction((value)-> Long.valueOf(String.valueOf(value)),0));
        VALIDATORS.put(Float.class.getName(), new FloatAndDoubleNumberFunction((value)-> Float.valueOf(String.valueOf(value)),null));
        VALIDATORS.put(float.class.getName(), new FloatAndDoubleNumberFunction((value)-> Float.valueOf(String.valueOf(value)),0.0));
        VALIDATORS.put(Double.class.getName(), new FloatAndDoubleNumberFunction((value)-> Double.valueOf(String.valueOf(value)),null));
        VALIDATORS.put(double.class.getName(), new FloatAndDoubleNumberFunction((value)-> Double.valueOf(String.valueOf(value)),0.0));
        BooleanFunction booleanFunction = new BooleanFunction();
        VALIDATORS.put(Boolean.class.getName(), booleanFunction);
        VALIDATORS.put(boolean.class.getName(), booleanFunction);
    }

    private static class Singleton {
        private static final FunctionManager VALIDATOR_MANAGER = new FunctionManager();
    }

    public static FunctionManager getInstance() {
        return Singleton.VALIDATOR_MANAGER;
    }

    public ValidateResult doValidate(String classType, Object vaue) {
        if(getVALIDATORS().containsKey(classType)) {
            return (ValidateResult)getVALIDATORS().get(classType).apply(vaue);
        }
        return ValidateResult.buildSuccessValidateResult("空对象，无需转换",null);
    }

    private static Map<String, Function> getVALIDATORS() {
        return VALIDATORS;
    }
}
