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

    private ValidatorManager(){
        VALIDATORS.put(String.class.getName(), new StringValidator());
        IntegerValidator integerValidator = new IntegerValidator();
        VALIDATORS.put(Integer.class.getName(), integerValidator);
        VALIDATORS.put(int.class.getName(), integerValidator);
        LongValidator longValidator = new LongValidator();
        VALIDATORS.put(Long.class.getName(), longValidator);
        VALIDATORS.put(long.class.getName(), longValidator);
        FloatValidator floatValidator = new FloatValidator();
        VALIDATORS.put(Float.class.getName(), floatValidator);
        VALIDATORS.put(float.class.getName(), floatValidator);
        DoubleValidator doubleValidator = new DoubleValidator();
        VALIDATORS.put(Double.class.getName(), doubleValidator);
        VALIDATORS.put(double.class.getName(), doubleValidator);
        BooleanValidator booleanValidator = new BooleanValidator();
        VALIDATORS.put(Boolean.class.getName(), booleanValidator);
        VALIDATORS.put(boolean.class.getName(), booleanValidator);
    }

    private static class Singleton {
        private static final ValidatorManager VALIDATOR_MANAGER = new ValidatorManager();
    }

    public static ValidatorManager getInstance() {
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
