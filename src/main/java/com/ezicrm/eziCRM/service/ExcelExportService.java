package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.annotation.ExportedField;
import com.ezicrm.eziCRM.repository.CustomerRepository;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.*;

@Service
public class ExcelExportService {

    public ExcelExportService() {

    }

    private void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof java.util.Date) {
            cell.setCellValue((java.util.Date) value);
        } else {
            // Xử lý các kiểu dữ liệu khác nếu cần
            cell.setCellValue(value.toString());
        }
    }

    public <T> InputStreamResource writeToFile(List<T> objs) {
        // Tạo file Excel
        Workbook workbook = new XSSFWorkbook();

        if (!objs.isEmpty()) {
            Sheet sheet = workbook.createSheet(objs.get(0).getClass().getSimpleName());
            int row = objs.size();
            int col = objs[0].get
            for (int i = -1; i < objs.size(); i++) {
                for (int j = 0; j < )
                if (i == -1) {
                    Row headerRow = sheet.createRow(0);

                }
            }

            System.out.println(Arrays.toString(fields));

            int i = 0;
            for (Field field : fields) {
                headerRow.createCell(i).setCellValue(field.getName());
                i++;
            }

            for (i = 0; i < objs.size(); i++) {
                Row dataRow = sheet.createRow(i);
                for (int j = 0; j < fields.length; j++)
                    setCellValue(dataRow.createCell(j), );
            }

            // Ghi file Excel vào ByteArrayOutputStream
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                workbook.write(outputStream);
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

            return new InputStreamResource(inputStream);
        } else {
            throw new IllegalArgumentException("Invalid args, no object found");
        }

    }
}