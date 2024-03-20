package com.ezicrm.eziCRM.service;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CRUDService<CategoryEntity>{
    private final CategoryRepository repository;
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }
    @Override
    public Optional<CategoryEntity> getByID(Long id) throws IllegalArgumentException{
        return repository.findById(id);
    }

    @Override
    public List<CategoryEntity> getAll(){
        return repository.findAll();
    }

    @Override
    public Optional<CategoryEntity> insert(CategoryEntity entity) throws IllegalArgumentException{
        boolean checkCategoryName = repository.existsByCategoryName(entity.getCategoryName());
        if(!checkCategoryName){
            repository.save(entity);
            return Optional.of(entity);
        }
        else throw new IllegalArgumentException("Invalid category name, category name has been taken.");    }

    @Override
    public Optional<CategoryEntity> update(CategoryEntity entity) throws IllegalArgumentException{
        boolean checkCategoryName = repository.existsByCategoryName(entity.getCategoryName());
        if(!checkCategoryName){
            repository.save(entity);
            return Optional.of(entity);
        }
        else throw new IllegalArgumentException("Invalid category name, category name has been taken.");    }

    @Override
    public boolean delete(Long id) {
        Optional<CategoryEntity> entity = repository.findById(id);
        if(entity.isPresent()){
            repository.delete(entity.get());
            return true;
        }
        return false;
    }
}
