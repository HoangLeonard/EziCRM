package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.model.CustomerEntity;
import com.ezicrm.eziCRM.repository.CategoryRepository;
import com.ezicrm.eziCRM.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
        category.get().addCustomer(customer.get());
        return Optional.of(categoryRepository.save(category.get()));
    }

    public Optional<CategoryEntity> deleteCustomerOnCategory(Long catId, Long cusId){
        Optional<CategoryEntity> category = categoryRepository.findById(catId);
        Optional<CustomerEntity> customer = customerRepository.findById(cusId);
        if(category.isEmpty() || customer.isEmpty()){
            return Optional.empty();
        }
        category.get().deleteCustomer(customer.get());
        return Optional.of(categoryRepository.save(category.get()));
    }

    public List<CustomerEntity> getCustomersToCategory(Long id){
        Optional<CategoryEntity> category = categoryRepository.findById(id);
        return category.get().getCustomersToCategory();
    }

    public List<CategoryEntity> getCategoriesToCustomer(Long id){
        Optional<CustomerEntity> customer = customerRepository.findById(id);
        return customer.get().getCategories().stream().toList();
    }

    public Optional<CustomerEntity> addCategoriesToCustomer(List<Long> categoryIds, Long cusId){
        List<CategoryEntity> categoryEntities = categoryRepository.findAllById(categoryIds);
        Optional<CustomerEntity> customer = customerRepository.findById(cusId);
        // chua save Category
        customer.get().getCategories().addAll(categoryEntities);

        return Optional.of(customerRepository.save(customer.get()));
    }

    public CustomerRepository customerRepository(){
        return this.customerRepository;
    }
}
