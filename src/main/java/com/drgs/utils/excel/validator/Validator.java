package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;

/**
 * @author wangyao
 */
public interface Validator<T> {

    ValidateResult validate(T value);
}
