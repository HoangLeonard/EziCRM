package com.ezicrm.eziCRM.controller;

import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.model.ResponseDTO;
import com.ezicrm.eziCRM.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Customers")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    ResponseEntity<ResponseDTO> getAllCustomers() {
        List<CustomerEntity> res = repository.findAll();
        return !res.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "successfully query customer.", res)
                ):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "no customer found.", res)
                );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getCustomerById(@PathVariable Long id) {
        Optional<CustomerEntity> foundCustomer = repository.findById(id);
        return foundCustomer.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body (
                        new ResponseDTO("ok", "successfully query customer.", foundCustomer)
                ):
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseDTO("fail", "no customer with id = " + id + " found.", "")
                );
    }

    @PostMapping(path = "/insert")
    ResponseEntity<ResponseDTO> insertCustomer(@RequestBody CustomerEntity newCustomer) {
        CustomerEntity foundCustomers = repository.findFirstByCic(newCustomer.getCic().trim());
        if(foundCustomers != null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseDTO("fail", "customer with cic = " + newCustomer.getCic() + " has been exits", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("ok", "insert customer successfully", repository.save(newCustomer))
        );
    }
}
