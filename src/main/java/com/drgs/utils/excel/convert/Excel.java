package com.drgs.utils.excel.convert;

import com.drgs.utils.excel.exception.FileNotExistsException;
import com.drgs.utils.excel.exception.NotExcelFileException;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * @author wangyao
 */
@Slf4j
public class Excel {

    private static final Set<String> EXTENSION = Sets.newHashSet(".xls","xlsx");

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
            log.error("创建HSSFWorkbook失败！",e);
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
}
