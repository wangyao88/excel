package com.drgs.utils.excel.bean;

import lombok.Data;

/**
 * excel数据坐标点 sheet row
 * @author wangyao
 */
@Data
public class Point {

    private int sheet;
    private int row;

    public Point(int sheet, int row) {
        this.sheet = sheet;
        this.row = row;
    }

    public static Point getInstance(int sheet, int row) {
        return new Point(sheet,row);
    }
}
