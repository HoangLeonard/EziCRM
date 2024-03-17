package com.ezicrm.eziCRM.controller;

import com.ezicrm.eziCRM.model.CategoryEntity;
import com.ezicrm.eziCRM.model.ResponseDTO;
import com.ezicrm.eziCRM.service.RelaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/Rela")
public class RelaController {
    private final RelaService service;

    public RelaController(RelaService service) {
        this.service = service;
    }

    // Relation API
    @PutMapping("/{catId}/Customer/{cusId}")
    ResponseEntity<ResponseDTO> addCustomerToCategory(
            @PathVariable Long catId,
            @PathVariable Long cusId
    ) {
        Optional<CategoryEntity> categoryEntity = service.addCustomerToCategory(catId, cusId);
        return categoryEntity.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Added record successfully.", categoryEntity)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Add record failed.", categoryEntity)
                );
    }

    @DeleteMapping("/{catId}/Customer/{cusId}")
    ResponseEntity<ResponseDTO> deleteCustomerOnCategory(
            @PathVariable Long catId,
            @PathVariable Long cusId
    ) {
        Optional<CategoryEntity> categoryEntity = service.deleteCustomerOnCategory(catId, cusId);
        return categoryEntity.isPresent()?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseDTO("ok", "Delete record successfully.", categoryEntity)
                ):
                ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseDTO("error", "Delete record failed.", categoryEntity)
                );
    }
}
