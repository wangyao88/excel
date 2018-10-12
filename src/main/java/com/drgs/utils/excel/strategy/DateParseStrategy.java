package com.drgs.utils.excel.strategy;

import com.drgs.utils.excel.exception.ValidateException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 单元格解析策略
 * @author wangyao
 */
public class DateParseStrategy{

    public Date parse(Object value, String partten) throws ValidateException {
        if(value instanceof Date) {
            return (Date)value;
        }
        if(value instanceof String) {
            SimpleDateFormat sdf = new SimpleDateFormat(partten);
            try {
                return sdf.parse(String.valueOf(value));
            } catch (ParseException e) {
                throw new ValidateException("输入值非法，无法转化为日期类型");
            }
        }
        throw new ValidateException("输入值非法，无法转化为日期类型");
    }

    public static void main(String[] args) {
        Object date = new Date();
        System.out.println(date instanceof Date);
    }
}
