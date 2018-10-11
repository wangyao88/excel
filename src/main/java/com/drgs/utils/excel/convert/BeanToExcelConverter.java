package com.drgs.utils.excel.convert;

import com.drgs.utils.excel.bean.Coordinate;
import com.drgs.utils.excel.bean.Header;
import com.drgs.utils.excel.bean.Result;
import com.drgs.utils.excel.exception.FileNotExistsException;
import com.drgs.utils.excel.exception.NotExcelFileException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * @author wangyao
 */
@Slf4j
public class BeanToExcelConverter {

    private List<Coordinate> coordinates = Lists.newArrayList();
    private List<Header> headers = Lists.newArrayList();

    public static BeanToExcelConverter getInstance() {
        BeanToExcelConverter beanToExcelConverter = new BeanToExcelConverter();
        Coordinate coordinate = new Coordinate();
        coordinate.setSheetIndex(0);
        coordinate.setStartRowIndex(0);
        beanToExcelConverter.setCoordinates(Lists.newArrayList(coordinate));
        return beanToExcelConverter;
    }

    public BeanToExcelConverter setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public BeanToExcelConverter setHeaders(List<Header> headers) {
        this.headers = headers;
        return this;
    }

    public <T> Result<T> toExcel(List<T> beans, String filePath) {
        File file = new File(filePath);
        Optional<Workbook> workbookOptional;
        Result<T> result = new Result<T>();
        try {
            workbookOptional = Excel.getWorkbook(file);
        } catch (FileNotExistsException | NotExcelFileException e) {
            log.error("导出excel失败",e);
            return result;
        }
        if(workbookOptional.isPresent()){
            Workbook workbook = workbookOptional.get();
            this.coordinates.forEach(coordinate -> toCoordinateExcel(coordinate,beans));
        }
        return null;
    }

    private <T> void toCoordinateExcel(Coordinate coordinate, List<T> beans) {

    }

}
