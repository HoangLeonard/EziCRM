package com.ezicrm.eziCRM.controller;

import com.ezicrm.eziCRM.model.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/v1/ImportData")
public class ImportDataController {
    // controller for import file
    @PostMapping("")
    public ResponseEntity<ResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
        return null;
    }

}
