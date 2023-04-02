package com.example.bidunitedapi.bidunitedapi.controller;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.dto.SavedProductDto;
import com.example.bidunitedapi.bidunitedapi.dto.UploadProductRequestDto;
import com.example.bidunitedapi.bidunitedapi.dto.UploadRequestOutputDto;
import com.example.bidunitedapi.bidunitedapi.service.ProductService;
import com.example.bidunitedapi.bidunitedapi.service.SavedProductService;
import com.example.bidunitedapi.bidunitedapi.service.UploadProductRequestService;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UploadProductRequestService uploadProductRequestService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SavedProductService savedProductService;

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

    @GetMapping("/user/my-requests")
    public ResponseEntity<List<UploadRequestOutputDto>> getRequestsByUser(@RequestParam("username") String username){
        try {
            List<UploadRequestOutputDto> list=uploadProductRequestService.getRequestsByUser(username);
            return new ResponseEntity(list,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/my-requests/pending")
    public ResponseEntity<List<UploadRequestOutputDto>> getRequestsPendingByUser(@RequestParam("username") String username){
        try {
            List<UploadRequestOutputDto> list=uploadProductRequestService.getRequestsPendingByUser(username);
            return new ResponseEntity(list,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/my-requests/declined")
    public ResponseEntity<List<UploadRequestOutputDto>> getRequestsDeclinedByUser(@RequestParam("username") String username){
        try {
            List<UploadRequestOutputDto> list=uploadProductRequestService.getRequestsDeclinedByUser(username);
            return new ResponseEntity(list,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/my-requests/delete/{id}")
    public ResponseEntity<Void> declineRequest(@PathVariable("id")Long id){
        try {
            uploadProductRequestService.deleteById(id);

            HttpStatus status=HttpStatus.OK;
            return  new ResponseEntity<>(status);
        }
        catch (Exception e){
            HttpStatus status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
    }

    @DeleteMapping("/user/my-requests/listed/delete/{id}")
    public ResponseEntity<Void> deleteRequestandProduct(@PathVariable("id")Long id){
        try {
            ProductDto productDto=productService.findByRequestId(id);

            uploadProductRequestService.deleteById(id);
            productService.deleteById(Long.parseLong(productDto.getId()));


            HttpStatus status=HttpStatus.OK;
            return  new ResponseEntity<>(status);
        }
        catch (Exception e){
            HttpStatus status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
    }

    @PostMapping("/user/save")
    public ResponseEntity<Void> saveItem(@RequestBody Map<String, Long> credentials){
        try {
            SavedProductDto savedProductDto=new SavedProductDto();
            savedProductDto.setProductId(credentials.get("productId"));
            savedProductDto.setUserId(credentials.get("userId"));

            savedProductService.addSavedProduct(savedProductDto);

            HttpStatus status=HttpStatus.OK;

            return  new ResponseEntity<>(status);
        }
        catch (Exception e){
            HttpStatus status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
    }
}
