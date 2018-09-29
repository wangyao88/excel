package com.drgs.utils.excel.function;

import java.util.function.Function;

/**
 * @author wangyao
 */
public class FloatAndDoubleNumberFunction extends BaseNumberFunction {

    public FloatAndDoubleNumberFunction(Function function, Object nullData) {
        super(function, nullData);
    }

    @Override
    protected boolean isNeedSubString() {
        return false;
    }

    @Override
    protected boolean isValida(String value) {
        return super.isFloatOrDouble(value);
    }
}
