package com.drgs.utils.excel.convert;

import com.drgs.utils.excel.exception.FileNotExistsException;
import com.drgs.utils.excel.exception.NotExcelFileException;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * @author wangyao
 */
public class Excel {

    private static final Set<String> EXTENSION = Sets.newHashSet(".xls","xlsx");
    private static final Map<Integer, Function<Cell,Object>> CELL_TYPE_FUNCTION = Maps.newHashMap();

    static {
        CELL_TYPE_FUNCTION.put(Cell.CELL_TYPE_STRING, (cell) -> cell.getStringCellValue());
        CELL_TYPE_FUNCTION.put(Cell.CELL_TYPE_BOOLEAN, (cell) -> cell.getBooleanCellValue());
        CELL_TYPE_FUNCTION.put(Cell.CELL_TYPE_BLANK, (cell) -> StringUtils.EMPTY);
        CELL_TYPE_FUNCTION.put(Cell.CELL_TYPE_NUMERIC, (cell) -> {
                    return getNumericCellValue(cell);
        });
    }

    private static Object getNumericCellValue(Cell cell) {
        if(HSSFDateUtil.isCellDateFormatted(cell)) {
            return cell.getDateCellValue();
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(cell.getNumericCellValue());
    }

    public static Optional<Workbook> getWorkbook(File file) throws FileNotExistsException, NotExcelFileException {
        if (!file.exists()) {
            throw new FileNotExistsException();
        }
        String extension = Files.getFileExtension(file.getPath());
        if (!file.isFile() || !EXTENSION.contains(extension)) {
            throw new NotExcelFileException();
        }
        try {
            return Optional.of(getWorkbook(file,extension));
        }catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Workbook getWorkbook(File file, String extension) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        if(".xls".equals(extension)) {
            return new HSSFWorkbook(fileInputStream);
        }
        return new XSSFWorkbook(fileInputStream);
    }

    public static Object getCellValue(Cell cell) {
        if(Objects.isNull(cell)){
            return StringUtils.EMPTY;
        }
        int cellType = cell.getCellType();
        if(!CELL_TYPE_FUNCTION.containsKey(cellType)){
            return StringUtils.EMPTY;
        }
        return CELL_TYPE_FUNCTION.get(cellType).apply(cell);
    }

}
