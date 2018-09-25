package com.drgs.utils.excel.bean;

import lombok.Data;

/**
 * excel数据坐标点 sheet row
 * @author wangyao
 */
@Data
public class Point implements Comparable<Point>{

    private int sheet;
    private int row;

    public Point(int sheet, int row) {
        this.sheet = sheet;
        this.row = row;
    }

    public static Point getInstance(int sheet, int row) {
        return new Point(sheet,row);
    }

    @Override
    public int compareTo(Point other) {
        return Integer.valueOf(this.getSheet() + this.getRow()).compareTo(other.getSheet() + other.getRow());
    }
}
