package com.ezicrm.eziCRM.controller;

import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/Customers")
public class CustomerController {

    private final CustomerRepository repository;

    public CustomerController(CustomerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    List<CustomerEntity> getAllCustomers() {
        System.out.println("aaaa");
        return repository.findAll();
    }

}
