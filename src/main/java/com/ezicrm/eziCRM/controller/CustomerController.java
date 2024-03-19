package com.ezicrm.eziCRM.controller;


import com.ezicrm.eziCRM.model.CusSearchReqDTO;
import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.model.ResponseDTO;
import com.ezicrm.eziCRM.service.CustomerService;
import com.ezicrm.eziCRM.service.ExcelHandlerService;
import jakarta.validation.Valid;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Customers")
public class CustomerController {

    private final CustomerService customerService;
    private final ExcelHandlerService excelHandlerService;

    public CustomerController(CustomerService customerService, ExcelHandlerService excelHandlerService) {
        this.customerService = customerService;
        this.excelHandlerService = excelHandlerService;
    }

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
//    }

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


}
