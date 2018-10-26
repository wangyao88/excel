package com.drgs.utils.excel.bean;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wangyao
 */
@Data
@NoArgsConstructor
public class Result<T> implements Serializable {

    private Map<Point, T> successBeans = Maps.newConcurrentMap();
    private Map<Point, String> failureBeans = Maps.newConcurrentMap();
    private boolean success = true;
}

