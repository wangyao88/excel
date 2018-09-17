package com.drgs.utils.excel.exception;

import lombok.ToString;

/**
 * excel文件不存在 异常
 * @author wangyao
 */
@ToString
public class FileNotExistsException extends ExcelException {

    private static final String DEFAULT_MSG = "EXCEL文件不存在";

    public FileNotExistsException() {
        super(DEFAULT_MSG);
    }

    public FileNotExistsException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
