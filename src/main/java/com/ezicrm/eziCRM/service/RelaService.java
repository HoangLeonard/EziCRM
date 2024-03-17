package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.repository.CategoryRepository;
import com.ezicrm.eziCRM.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelaService {
    private final CustomerRepository customerRepository;
    private final CategoryRepository categoryRepository;

    public RelaService(CustomerRepository customerRepository, CategoryRepository categoryRepository) {
        this.customerRepository = customerRepository;
        this.categoryRepository = categoryRepository;
    }

    public Optional<CategoryEntity> addCustomerToCategory(Long catId, Long cusId){
        Optional<CategoryEntity> category = categoryRepository.findById(catId);
        Optional<CustomerEntity> customer = customerRepository.findById(cusId);
        if(category.isEmpty() || customer.isEmpty()){
            return Optional.empty();
        }
        category.get().getAssCustomers().add(customer.get());
        return Optional.of(categoryRepository.save(category.get()));
    }

    public Optional<CategoryEntity> deleteCustomerOnCategory(Long catId, Long cusId){
        Optional<CategoryEntity> category = categoryRepository.findById(catId);
        Optional<CustomerEntity> customer = customerRepository.findById(cusId);
        if(category.isEmpty() || customer.isEmpty()){
            return Optional.empty();
        }
        category.get().getAssCustomers().remove(customer.get());
        return Optional.of(categoryRepository.save(category.get()));
    }


}
