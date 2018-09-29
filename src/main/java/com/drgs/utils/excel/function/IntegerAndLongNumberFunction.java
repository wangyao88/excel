package com.drgs.utils.excel.function;

import java.util.function.Function;

/**
 * @author wangyao
 */
public class IntegerAndLongNumberFunction extends BaseNumberFunction {

    public IntegerAndLongNumberFunction(Function function, Object nullData) {
        super(function, nullData);
    }

    @Override
    protected boolean isNeedSubString() {
        return true;
    }

    @Override
    protected boolean isValida(String value) {
        return super.isIntegerOrLong(value);
    }
}
