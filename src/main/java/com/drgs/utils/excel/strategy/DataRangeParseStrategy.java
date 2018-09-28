package com.drgs.utils.excel.strategy;

import com.drgs.utils.excel.bean.Foo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 单元格解析策略
 * @author wangyao
 */
public class DataRangeParseStrategy implements RangeParseStrategy{

    @Override
    public List<String> parse(Object[] objs) {
        int position = 7;
        Object obj = objs[7];
        if(Objects.isNull(obj)){
            Lists.newArrayList();
        }
        String str = (String) obj;
        String[] split = str.split(",");
        return Lists.newArrayList(split);
    }
}
