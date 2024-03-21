package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.ExportDTO;
import com.ezicrm.eziCRM.model.Exportable;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelHandlerService {

    final String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    public ExcelHandlerService() {

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
            int col = objs.get(0).exportData().size();
            Row headerRow = sheet.createRow(0);
            Map<Integer, ExportDTO> dataMap = objs.get(0).exportData();

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
                dataMap = objs.get(i).exportData();
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
            return new InputStreamResource(new ByteArrayInputStream(new byte[0]));
        }
    }

    private boolean isExcelFile(MultipartFile file) {
        final String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        return Objects.equals(file.getContentType(), contentType);
    }

    public List<List<String>> readFromFile(MultipartFile file) throws IOException {

        if (!isExcelFile(file)) throw new IllegalArgumentException("File must be of type Excel (XLSX)");

        List<List<String>> data = new ArrayList<>();

        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            List<String> rowData = new ArrayList<>();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case STRING:
                        rowData.add(cell.getStringCellValue());
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            // Nếu ô Excel có kiểu dữ liệu là ngày tháng, chuyển đổi sang chuỗi
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            rowData.add(sdf.format(cell.getDateCellValue()));
                        } else {
                            // Nếu không phải ngày tháng, thêm giá trị số vào danh sách
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                        }
                        break;
                    case BOOLEAN:
                        rowData.add(String.valueOf(cell.getBooleanCellValue()));
                        break;
                    default:
                        rowData.add("");
                }
            }
            if (rowData.size() == 6) data.add(rowData);
            else break;
        }

        workbook.close();
        return data;
    }
}