package com.ezicrm.eziCRM.controller;


import com.ezicrm.eziCRM.model.CusSearchReqDTO;
import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.model.ResponseDTO;
import com.ezicrm.eziCRM.service.CustomerService;
import com.ezicrm.eziCRM.service.ExcelHandlerService;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping(path = "/api/v1/Customers")
public class CustomerController {

    private final CustomerService customerService;
    private final ExcelHandlerService excelHandlerService;
    private final EntityManager entityManager;

    private List<CustomerEntity> validCustomer;
    private List<CustomerEntity> invalidCustomer;

    public CustomerController(CustomerService customerService, ExcelHandlerService excelHandlerService, EntityManager entityManager) {
        this.customerService = customerService;
        this.excelHandlerService = excelHandlerService;
        this.entityManager = entityManager;
    }

    @GetMapping("")
    ResponseEntity<ResponseDTO> getAllCustomers() {
        List<CustomerEntity> res = customerService.getAll();
        return !res.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "found " + res.size() + " customers.", res)
                ):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "no customer found.", res)
                );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getCustomerById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO("fail", "Invalid ID, ID must not be null", "")
            );
        }

        Optional<CustomerEntity> foundCustomer = customerService.getByID(id);
        return foundCustomer.isPresent() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "successfully query customer.", foundCustomer)
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseDTO("fail", "no customer with id = " + id + " found.", "")
                );
    }

    @PostMapping(path = "/insert")
    ResponseEntity<ResponseDTO> insertCustomer( @Valid @RequestBody CustomerEntity newCustomer) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "successfully insert new customer", customerService.insert(newCustomer))
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO("fail", "Cannot insert new customer", e.getMessage())
            );
        }
    }

    @PutMapping(path = "/update")
    ResponseEntity<ResponseDTO> updateCustomer(@Valid @RequestBody CustomerEntity newCustomer) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "successfully update new customer", customerService.update(newCustomer))
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO("fail", "Cannot update new customer", e.getMessage())
            );
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<ResponseDTO> deleteCustomer(@PathVariable Long id) {
        if (customerService.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "successfully delete customer id = "+ id, "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseDTO("fail", "Cannot found customer id = " + id, "")
            );
        }
    }

    @PostMapping(path = "/search")
    ResponseEntity<ResponseDTO> getCustomerByProperty(@RequestBody CusSearchReqDTO cusSearchReqDTO) {
        List<CustomerEntity> res = customerService.getCustomerByProperty(cusSearchReqDTO);
        return !res.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "found " + res.size() + " customers.", res)
                ):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "no customer found.", res)
                );
    }

    @PostMapping(path = "/export/id")
    public ResponseEntity<InputStreamResource> exportCustomerToExcelByID(@RequestBody Long[] ids) {
        if (ids == null) { throw new IllegalArgumentException("Invalid id list, id list binding as 'ids' key word must not be null.");}

        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        List<CustomerEntity> res = customerService.getAllByID(Arrays.stream(ids).toList());
        InputStreamResource resource = excelHandlerService.writeToFile(res);
        // Trả về ResponseEntity chứa InputStreamResource
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customer_" + fileName + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping(path = "/export")
    public ResponseEntity<InputStreamResource> exportCustomerToExcel(@RequestBody List<CustomerEntity> customers) {
        if (customers.isEmpty()) throw new IllegalArgumentException("Invalid, empty customers.");

        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        InputStreamResource resource = excelHandlerService.writeToFile(customers);
        // Trả về ResponseEntity chứa InputStreamResource
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customer_" + fileName + ".xlsx")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @PostMapping("/findByCategoryIds")
    ResponseEntity<ResponseDTO> findByCategoryIds(@RequestBody List<Long> categoryIds){
        try{
            List<CustomerEntity> customers = customerService.findByCategoryIds(categoryIds);
            return !customers.isEmpty() ?
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseDTO("ok", "Search success", customers)
                    ) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new ResponseDTO("Not found", "no customer found", customers)
                    );
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO("fail", "Error", e.getMessage())
            );
        }
    }

    @PostMapping("/import")
    public ResponseEntity<ResponseDTO> importCustomerFromExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<List<String>> data = excelHandlerService.readFromFile(file);
            List<String> expectedHeader = Arrays.asList("name", "address", "birth", "phone", "email", "facebook");
            List<String> currentHeader = data.remove(0);
            if (!currentHeader.equals(expectedHeader)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseDTO("fail", "File template not match", "Expected header " + Arrays.toString(expectedHeader.toArray()) + " found " + Arrays.toString(currentHeader.toArray()))
                );
            } else {
                List<CustomerEntity> customers = customerService.customerParsing(data);
                customerService.createTemporaryTable();
                customerService.addToTmpCustomers(customers);
                customerService.checkDuplicate(customers);

                List<CustomerEntity> validCustomers = new ArrayList<>();
                List<CustomerEntity> invalidCustomers = new ArrayList<>();
                Map<String, String> errorMessage = new HashMap<>();
                for (CustomerEntity customer : customers) {
                    if (customer.getErrors().isEmpty()) {
                        validCustomers.add(customer);
                    } else {
                        invalidCustomers.add(customer);
                        long tmp = customer.getCusId() + 1;
                        errorMessage.put("Line " + tmp + ":", customer.extractErrorMessage());
                    }
                }

                this.validCustomer = validCustomers;
                this.invalidCustomer = invalidCustomers;

                return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(
                        new ResponseDTO("ok", "return unsuccessfully import data.", errorMessage)
                );

            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDTO("fail", "Invalid file type", e.getMessage())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    new ResponseDTO("fail", "Cannot handle file", "An error occurred while reading the Excel file.")
            );
        }
    }

    @GetMapping("/import/flush/{mode}")
    public ResponseEntity<InputStreamResource> flushAndImport(@PathVariable String mode) {
        if (mode.equals("cancel")) {
            this.invalidCustomer = null;
            this.validCustomer = null;
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        } else if (mode.equals("export")) {

            if (validCustomer != null) {
                for (CustomerEntity c : validCustomer) customerService.insert(c);
            }

            if (this.invalidCustomer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            } else {
                String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                InputStreamResource resource = excelHandlerService.writeToFile(this.invalidCustomer);
                // Trả về ResponseEntity chứa InputStreamResource
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=customer_" + fileName + ".xlsx")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
        }
    }
}
