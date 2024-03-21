package com.ezicrm.eziCRM.controller;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.model.ResponseDTO;
import com.ezicrm.eziCRM.service.RelaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Rela")
public class RelaController {
    private final RelaService service;

    public RelaController(RelaService service) {
        this.service = service;
    }

    // Relation API
    @GetMapping("category/{catId}")
    ResponseEntity<ResponseDTO> findCustomersOnCategory(
            @PathVariable Long catId
    ) {
        List<CustomerEntity> customers = service.getCustomersToCategory(catId);
        return !customers.isEmpty()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "ok", customers)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "error", null)
                );
    }

    @GetMapping("/customer/{cusId}")
    ResponseEntity<ResponseDTO> findCategoriesOnCustomer(
            @PathVariable Long cusId
    ) {
        List<CategoryEntity> categories = service.getCategoriesToCustomer(cusId);
        return !categories.isEmpty()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "ok", categories)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "error", null)
                );
    }

    @PutMapping("/{catId}/Customer/{cusId}")
    ResponseEntity<ResponseDTO> addCustomerToCategory(
            @PathVariable Long catId,
            @PathVariable Long cusId
    ) {
        Optional<CustomerEntity> customerEntity = service.addCustomerToCategory(catId, cusId);
        return customerEntity.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Added record successfully.", customerEntity)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Add record failed.", "")
                );
    }

    @DeleteMapping("/{catId}/Customer/{cusId}")
    ResponseEntity<ResponseDTO> deleteCustomerOnCategory(
            @PathVariable Long catId,
            @PathVariable Long cusId
    ) {
        Optional<CustomerEntity> customerEntity = service.deleteCustomerOnCategory(catId, cusId);
        return customerEntity.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Delete record successfully.", customerEntity)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Delete record failed.", "")
                );
    }

    @PostMapping("/{cusId}/addCategories")
    ResponseEntity<ResponseDTO> addCategoriesToCustomer(@RequestBody List<Long> catIds, @PathVariable Long cusId){
        Optional<CustomerEntity> customer = service.addCategoriesToCustomer(catIds, cusId);
        return customer.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Updated record successfully.", customer)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Updated record failed.", "")
                );
    }

    @PostMapping("/addCategoriesToCustomers")
    ResponseEntity<ResponseDTO> addCategoriesToCustomers(@RequestBody Map<String, Long[]> reqMap)
    {
        List<Long> catIds = Arrays.stream(reqMap.get("catIds")).toList();
        List<Long> cusIds = Arrays.stream(reqMap.get("cusIds")).toList();

        cusIds.forEach(cusId -> service.addCategoriesToCustomer(catIds, cusId));

        List<CustomerEntity> customerEntities = service.customerRepository().findAllById(cusIds);

        return !customerEntities.isEmpty()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Updated record successfully.", customerEntities)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Updated record failed.", "")
                );
    }
}
