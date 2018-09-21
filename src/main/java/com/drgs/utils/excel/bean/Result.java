package com.drgs.utils.excel.bean;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @author wangyao
 */
@Data
public class Result<T> {

    private Map<Point, T> successBeans = Maps.newHashMap();
    private Map<Point, String> failureBeans = Maps.newHashMap();
    private boolean success = true;
}

