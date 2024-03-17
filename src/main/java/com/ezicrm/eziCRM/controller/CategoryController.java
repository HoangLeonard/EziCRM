package com.ezicrm.eziCRM.controller;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.model.ResponseDTO;
import com.ezicrm.eziCRM.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/v1/Category")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service)
    {
        this.service = service;
    }

    @GetMapping("")
    ResponseEntity<ResponseDTO> getAllCategories() {
        List<CategoryEntity> res = service.getAll();
        return !res.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Search success.", res)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Not found.", res)
                );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getCategoryById(@PathVariable Long id){
        Optional<CategoryEntity> foundCategory = service.getByID(id);
        return foundCategory.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Search success.", foundCategory)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseDTO("error", "Not found.", foundCategory)
                );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseDTO> insertCategory(@RequestBody CategoryEntity category){
        Optional<CategoryEntity> categoryEntity = service.insert(category);
        return categoryEntity.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Added record successfully.", categoryEntity)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Category name has been taken.", categoryEntity)
                );
    }

    @PutMapping("/update")
    ResponseEntity<ResponseDTO> updateCategory(@RequestBody CategoryEntity ignoredCategory){
        Optional<CategoryEntity> categoryEntity = service.update(ignoredCategory);
        return categoryEntity.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Updated record successfully.", categoryEntity)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Updated record failed.", categoryEntity)
                );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO> deleteCategory(@PathVariable Long id){
        boolean category = service.delete(id);

        if(category){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "Delete record successfully.","")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                new ResponseDTO("error", "Delete record failed.", "")
        );
    }
}
