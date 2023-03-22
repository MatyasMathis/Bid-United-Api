package com.example.bidunitedapi.bidunitedapi.controller;

import com.example.bidunitedapi.bidunitedapi.dto.UploadProductRequestDto;
import com.example.bidunitedapi.bidunitedapi.service.UploadProductRequestService;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UploadProductRequestService uploadProductRequestService;

    @PostMapping("/user/upload-request")
    public ResponseEntity<Void> addUploadRequest(@RequestBody Map<String, String> uploadRequest){
        System.out.println("Received upload request with parameters:"+ uploadRequest);
        String name=uploadRequest.get("name");
        String category=uploadRequest.get("category");
        int year=Integer.parseInt(uploadRequest.get("year"));
        String description=uploadRequest.get("description");
        String imagePath=uploadRequest.get("imagePath");
        int price =Integer.parseInt(uploadRequest.get("price"));
        LocalDate date=LocalDate.now();
        String uploadDate=date.toString();
        long userId=Long.parseLong(uploadRequest.get("userId"));

        UploadProductRequestDto uploadProductRequestDto=new UploadProductRequestDto(name,category,year,description,imagePath,price,false,uploadDate,userId);
        try {
            uploadProductRequestService.addProductRequest(uploadProductRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
