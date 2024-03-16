package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.repository.CategoryRepository;
import com.ezicrm.eziCRM.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements CRUDService<CategoryEntity>{

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public CategoryEntity getByID(Long id) {
        return null;
    }

    @Override
    public List<CategoryEntity> getAll() {
        return null;
    }

    @Override
    public CategoryEntity save(CategoryEntity entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
