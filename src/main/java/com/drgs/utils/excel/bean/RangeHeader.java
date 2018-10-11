package com.drgs.utils.excel.bean;

import lombok.Data;

/**
 * @author wangyao
 */
@Data
public class RangeHeader extends Header {

    private int startCell;
    private int endCell;

    public RangeHeader(String header, int startCell, int endCell) {
        super(header);
        this.startCell = startCell;
        this.endCell = endCell;
    }
}
