package com.drgs.utils.excel.bean;

import com.drgs.utils.excel.annotation.*;
import com.drgs.utils.excel.strategy.DataRangeParseStrategy;
import com.drgs.utils.excel.strategy.FooRangeParseStrategy;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Bean {

    @ExcelCell(position = 0)
    private String id;

    @ExcelSingleCellImplicit(position = 1, type = Age.class)
    private Age age;

    @ExcelCell(position = 2)
    private double cost;

    @ExcelCell(position = 3)
    private boolean success;

    @ExcelDateCell(position = 4, partten = "yyyy/MM/dd")
    private Date birthDay;

    @ExcelRangeImplicit(parseStrategy = FooRangeParseStrategy.class)
    private Foo foo;

    @ExcelRangeImplicit(parseStrategy = DataRangeParseStrategy.class)
    private List<String> data;
}
