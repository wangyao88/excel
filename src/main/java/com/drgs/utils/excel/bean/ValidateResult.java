package com.drgs.utils.excel.bean;

import lombok.Data;

/**
 * @author wangyao
 */
@Data
public class ValidateResult {

    private static final String DEFAULT_SUCCESS_MSG = "校验通过";
    private static final String DEFAULT_FAILURE_MSG = "校验失败";

    private boolean result;
    private String msg;
    private Throwable cause;
    private Object data;

    public static ValidateResult buildSuccessValidateResult(){
        return buildSuccessValidateResult(DEFAULT_SUCCESS_MSG);
    }

    public static ValidateResult buildSuccessValidateResult(String msg){
        ValidateResult validateResult = new ValidateResult();
        validateResult.setResult(true);
        validateResult.setMsg(msg);
        return validateResult;
    }

    public static ValidateResult buildFailureValidateResult(){
        return buildFailureValidateResult(DEFAULT_FAILURE_MSG);
    }

    public static ValidateResult buildFailureValidateResult(String msg){
        return buildFailureValidateResult(msg,null);
    }

    public static ValidateResult buildFailureValidateResult(String msg, Object data){
        return buildFailureValidateResult(msg,data,null);
    }

    public static ValidateResult buildFailureValidateResult(String msg, Object data, Throwable cause){
        ValidateResult validateResult = new ValidateResult();
        validateResult.setResult(false);
        validateResult.setMsg(msg);
        validateResult.setData(data);
        validateResult.setCause(cause);
        return validateResult;
    }
}
