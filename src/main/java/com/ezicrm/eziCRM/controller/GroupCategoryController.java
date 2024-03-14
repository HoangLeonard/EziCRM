package com.ezicrm.eziCRM.controller;

import com.ezicrm.eziCRM.model.GroupCategoryEntity;
import com.ezicrm.eziCRM.model.ResponseDTO;
import com.ezicrm.eziCRM.repository.GroupCategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/GroupCategory")
public class GroupCategoryController {
    private final GroupCategoryRepository repository;
    public GroupCategoryController(GroupCategoryRepository repository)
    {
        this.repository = repository;
    }

    @GetMapping("")
    ResponseEntity<ResponseDTO> getAllGroupCategories(){
        List<GroupCategoryEntity> groupCategoryEntities = repository.findAll();

        return !groupCategoryEntities.isEmpty() ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("oke", "Successfully query GC", groupCategoryEntities)
                ):
                ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("oke", "No GC found", groupCategoryEntities)
                );
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getGroupCategoryById(@PathVariable Long id){
        Optional<GroupCategoryEntity> groupCategoryEntity = repository.findById(id);
        return groupCategoryEntity.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("oke", "successfully query category.", groupCategoryEntity)
                ):
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("oke", "no group category found.", groupCategoryEntity)
                );
    }

    @PostMapping("/insert")
    ResponseEntity<ResponseDTO> insertCategory(@RequestBody GroupCategoryEntity groupCategory){
        boolean checkGroupCategory = repository.existsByGroupName(groupCategory.getGroupName());
        if(!checkGroupCategory){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "Insert successfully", repository.save(groupCategory))
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("Error", "Error", "")
        );
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDTO> deleteGroupCategory(@PathVariable Long id){
        boolean groupCategory = repository.existsById(id);

        if(groupCategory){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO("ok", "successfully query group category.","")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseDTO("oke", "no group category found.", "")
        );
    }
}
