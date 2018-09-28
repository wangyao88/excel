package com.drgs.utils.excel.validator;

import com.drgs.utils.excel.bean.ValidateResult;

public interface Validator {

    ValidateResult validate(Object value);
}
