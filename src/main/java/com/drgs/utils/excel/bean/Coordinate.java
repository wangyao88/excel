package com.drgs.utils.excel.bean;

import lombok.Data;

/**
 * execel解析坐标
 * @author wangyao
 */
@Data
public class Coordinate {

    private Integer sheetIndex;
    private Integer startRowIndex;
    private Integer endRowIndex;
}
