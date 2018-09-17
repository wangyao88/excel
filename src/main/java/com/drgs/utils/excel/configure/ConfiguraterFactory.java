package com.drgs.utils.excel.configure;

import com.drgs.utils.excel.annotation.ExcelCell;
import com.drgs.utils.excel.annotation.ExcelDateCell;
import com.drgs.utils.excel.annotation.ExcelImplicit;
import com.drgs.utils.excel.annotation.ExcelSingleCellImplicit;
import com.google.common.collect.Maps;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author wangyao
 */
public class ConfiguraterFactory {

    @Getter
    private static Map<Class<? extends Annotation>, Configurater> configuraters = Maps.newHashMap();

    private ConfiguraterFactory(){
        configuraters.put(ExcelCell.class, new ExcelCellConfigurater());
        configuraters.put(ExcelDateCell.class, new ExcelDateCellConfigurater());
        configuraters.put(ExcelSingleCellImplicit.class, new ExcelSingleCellImplicitConfigurater());
        configuraters.put(ExcelImplicit.class, new ExcelImplicitConfigurater());
    }

    private static class Singleton {
        private static final ConfiguraterFactory MANAGER = new ConfiguraterFactory();
    }

    public static ConfiguraterFactory getInstance() {
        return Singleton.MANAGER;
    }

    public Map<Class<? extends Annotation>, Configurater> getConfiguraters() {
        return Maps.newHashMap(configuraters);
    }
}
