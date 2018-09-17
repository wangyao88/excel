package com.drgs.utils.excel.bean;

import com.drgs.utils.excel.annotation.ExcelCell;
import lombok.Data;

@Data
public class Foo {

    @ExcelCell(position = 0)
    private String name;

    @ExcelCell(position = 1)
    private double age;

}
