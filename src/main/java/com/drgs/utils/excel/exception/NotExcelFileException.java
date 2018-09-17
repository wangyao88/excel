package com.drgs.utils.excel.exception;

import lombok.ToString;

/**
 * 不是EXCEL文件 异常
 * @author wangyao
 */
@ToString
public class NotExcelFileException extends ExcelException {

    private static final String DEFAULT_MSG = "不是EXCEL文件";

    public NotExcelFileException() {
        super(DEFAULT_MSG);
    }

    public NotExcelFileException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
