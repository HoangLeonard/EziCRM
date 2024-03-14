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
                        new ResponseDTO("ok", "found " + res.size() + " customers.", res)
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
                new ResponseDTO("ok", "successfully insert new customer", repository.save(newCustomer))
        );
    }

    @PutMapping(path = "/update")
    ResponseEntity<ResponseDTO> updateCustomer(@RequestBody CustomerEntity newCustomer) {
        Long id = newCustomer.getCusId();
        Optional<CustomerEntity> updatedCustomer = repository.findById(id);
        if (updatedCustomer.isPresent()) {
            CustomerEntity c = updatedCustomer.get();
            c.setName(newCustomer.getName());
            c.setGender(newCustomer.getGender());
            c.setBirth(newCustomer.getBirth());
            c.setAddress(newCustomer.getAddress());
            c.setPhone(newCustomer.getPhone());
            c.setEmail(newCustomer.getEmail());
            c.setFacebook(newCustomer.getFacebook());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "Update product successfully", repository.save(c))
            );
        } else {
            CustomerEntity foundCustomers = repository.findFirstByCic(newCustomer.getCic().trim());
            if (foundCustomers != null) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("fail", "customer with cic = " + newCustomer.getCic() + " has been exits", "")
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "successfully insert new customer", repository.save(newCustomer))
            );
        }
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<ResponseDTO> inverseStatusCustomer(@PathVariable Long id) {
        Optional<CustomerEntity> foundCustomer = repository.findById(id);
        if (foundCustomer.isPresent()) {
            CustomerEntity c = foundCustomer.get();
            if (c.getStatus() == 0) {
                c.setStatus(1);
                return ResponseEntity.status(HttpStatus.OK).body (
                        new ResponseDTO("ok", "successfully activate customer.", foundCustomer)
                );
            } else {
                c.setStatus(0);
                return ResponseEntity.status(HttpStatus.OK).body (
                        new ResponseDTO("ok", "successfully deactivate customer.", foundCustomer)
                );
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseDTO("fail", "no customer with id = " + id + " found.", "")
        );
    }


}
