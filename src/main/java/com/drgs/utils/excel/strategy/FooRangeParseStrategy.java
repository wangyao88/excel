package com.drgs.utils.excel.strategy;

import com.drgs.utils.excel.bean.Foo;
import org.apache.commons.lang3.StringUtils;

/**
 * 单元格解析策略
 * @author wangyao
 */
public class FooRangeParseStrategy implements RangeParseStrategy{


    @Override
    public Foo parse(Object[] objs) {
        int start = 5;
        int end = 6;
        Foo foo = new Foo();
        foo.setName(String.valueOf(objs[start]));
        foo.setAge(Double.valueOf(objs[end]+StringUtils.EMPTY));
        return foo;
    }
}
