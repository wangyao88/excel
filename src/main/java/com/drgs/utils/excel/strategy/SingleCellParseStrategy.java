package com.drgs.utils.excel.strategy;

/**
 * 单元格解析策略
 * @author wangyao
 */
public interface SingleCellParseStrategy extends ParseStrategy{

    /**
     * 将单元格解析为实体对象
     * @return 实体对象
     */
    <T> T parse(Object value);
}
