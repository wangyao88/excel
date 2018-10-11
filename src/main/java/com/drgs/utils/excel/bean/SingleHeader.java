package com.drgs.utils.excel.bean;

import lombok.Data;

/**
 * @author wangyao
 */
@Data
public class SingleHeader extends Header {

    private int position;

    public SingleHeader(String header, int position) {
        super(header);
        this.position = position;
    }
}
