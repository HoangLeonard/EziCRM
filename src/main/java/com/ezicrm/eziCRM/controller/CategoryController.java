package com.ezicrm.eziCRM.controller;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.model.ResponseDTO;
import com.ezicrm.eziCRM.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/v1/Category")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("")
    ResponseEntity<ResponseDTO> getAllCategories() {
        List<CategoryEntity> res = repository.findAll();
        return !res.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "successfully query customer.", res)
                ):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "no customer found.", res)
                );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getCategoryById(@PathVariable Long id){
        Optional<CategoryEntity> foundCategory = repository.findById(id);
        return foundCategory.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "successfully query category.", foundCategory)
                ):
                ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "no category found.", foundCategory)
                );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseDTO> insertCategory(@RequestBody CategoryEntity category){
        boolean checkCategory = repository.existsByLabelName(category.getLabelName());
        if(!checkCategory){
            return ResponseEntity.status(HttpStatus.OK).body(
              new ResponseDTO("ok", "Insert successfully", repository.save(category))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseDTO("error", "Error", "")
        );
    }

    @PutMapping("/update")
    ResponseEntity<ResponseDTO> updateCategory(@RequestBody CategoryEntity category){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("ok", "Insert successfully", "")
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO> deleteCategory(@PathVariable Long id){
        boolean category = repository.existsById(id);

        if(category){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "successfully query category.","")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("ok", "no category found.", "")
        );
    }
}
