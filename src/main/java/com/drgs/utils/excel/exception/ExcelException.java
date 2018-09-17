package com.drgs.utils.excel.exception;

/**
 * 异常类
 * @author wangyao
 */
public class ExcelException extends Exception{

    public ExcelException() {
    }

    public ExcelException(String message) {
        super(message);
    }

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException(Throwable cause) {
        super(cause);
    }
}
