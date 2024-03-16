package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements CRUDService<CustomerEntity> {


    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerEntity getByID(Long id) {
        return null;
    }

    @Override
    public List<CustomerEntity> getAll() {
        return null;
    }

    @Override
    public CustomerEntity save(CustomerEntity entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
