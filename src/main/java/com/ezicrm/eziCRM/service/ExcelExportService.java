package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.ExportDTO;
import com.ezicrm.eziCRM.model.Exportable;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import java.io.ByteArrayOutputStream;

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
        } else if (value instanceof java.sql.Date) {
            // Chuyển đổi java.sql.Date thành LocalDate và định dạng lại ngày tháng
            LocalDate localDate = ((java.sql.Date) value).toLocalDate();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            cell.setCellValue(localDate.format(dateFormatter));
        } else if (value instanceof java.util.Date) {
            // Chuyển đổi java.util.Date thành LocalDate và định dạng lại ngày tháng
            LocalDate localDate = ((java.util.Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            cell.setCellValue(localDate.format(dateFormatter));
        } else {
            // Xử lý các kiểu dữ liệu khác nếu cần
            cell.setCellValue(value.toString());
        }
    }

    public <T extends Exportable> InputStreamResource  writeToFile(List<T> objs) {
        // Tạo file Excel
        Workbook workbook = new XSSFWorkbook();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        if (!objs.isEmpty()) {
            Sheet sheet = workbook.createSheet(objs.get(0).getClass().getSimpleName());
            int row = objs.size() + 1;
            int col = objs.get(0).getExportedData().size();
            Row headerRow = sheet.createRow(0);
            Map<Integer, ExportDTO> dataMap = objs.get(0).getExportedData();

            // Tạo một đối tượng để định dạng viền
            CellStyle borderStyle = workbook.createCellStyle();
            borderStyle.setBorderTop(BorderStyle.THIN);
            borderStyle.setBorderBottom(BorderStyle.THIN);
            borderStyle.setBorderLeft(BorderStyle.THIN);
            borderStyle.setBorderRight(BorderStyle.THIN);

            // Header
            for (int j = 0; j < col; j++) {
                Cell c = headerRow.createCell(j);
                c.setCellValue(dataMap.get(j).getName());
                c.setCellStyle(borderStyle);
            }

            // Data
            for (int i = 0; i < row-1; i++) {
                dataMap = objs.get(i).getExportedData();
                Row nextRow = sheet.createRow(i+1);
                for (int j = 0; j < col; j++) {
                    Cell c = nextRow.createCell(j);
                    setCellValue(c, dataMap.get(j).getValue());
                    c.setCellStyle(borderStyle);
                }
            }

            for (int j = 0; j < col; j++) {
                sheet.autoSizeColumn(j);
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