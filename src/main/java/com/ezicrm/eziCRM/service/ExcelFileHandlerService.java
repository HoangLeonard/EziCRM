package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.CustomerEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class ExcelFileHandlerService<T> {

    public static boolean isValidExcelFile(MultipartFile file) {
        String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return Objects.equals(file.getContentType(), contentType);
    }

    public void exportExcelFie(List<T> obj) {

    }

//    public List<CustomerEntity> getCustomersData(InputStream inputStream) {
//        List<CustomerEntity> customers = new ArrayList<>();
//
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
//            XSSFSheet sheet = workbook.getSheet("customers");
//
//            int rowIndex = 0;
//            for (Row row: sheet) {
//                if (rowIndex == 0) {
//                    rowIndex++;
//                    continue;
//                }
//                Iterator<Cell> cellIterator = row.iterator();
//                int cellIndex = 0;
//                CustomerEntity customer = new CustomerEntity();
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//                    switch (cellIndex) {
//                        case 0 : customer.setCusId((int) cell.getNumericCellValue());
////                        case 1 : customer.setFirstName(cell.getStringCellValue());
//                    }
//                    cellIndex++;
//                }
//                customers.add(customer);
//            }
//        } catch (IOException e) {
//            e.getStackTrace();
//        }
//        return customers;
//
//    }

}
