package com.drgs.utils.excel.strategy;

import java.util.Date;
import java.util.Objects;

/**
 * 单元格解析策略
 * @author wangyao
 */
public class DateParseStrategy implements SingleCellParseStrategy{

    @Override
    public Date parse(Object value) {
        return (Date)value;
    }
}
