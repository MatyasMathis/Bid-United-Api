package com.example.bidunitedapi.bidunitedapi.controller;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.dto.UploadProductRequestDto;
import com.example.bidunitedapi.bidunitedapi.dto.UploadRequestOutputDto;
import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.service.ProductService;
import com.example.bidunitedapi.bidunitedapi.service.UploadProductRequestService;
import com.example.bidunitedapi.bidunitedapi.service.UserService;
import org.hibernate.annotations.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class AdminController {

    @Autowired
    UploadProductRequestService uploadProductRequestService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @GetMapping("/admin/upload-requests")
    public ResponseEntity<List<UploadRequestOutputDto>> getAllRequests(){
        try{
            HttpStatus status = HttpStatus.OK;
            List<UploadRequestOutputDto> list=uploadProductRequestService.getAllUnApprovedRequests();
           return new ResponseEntity<>(list,status);
        }
        catch (Exception e)
        {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

    }

    @DeleteMapping("/admin/upload-requests/delete/{id}")
    public ResponseEntity<Void> declineRequest(@PathVariable("id")Long id){
        try {
            //uploadProductRequestService.deleteById(id);

            UploadProductRequestDto uploadProductRequestDto=uploadProductRequestService.findById(id);
            uploadProductRequestDto.setRejected(true);
            uploadProductRequestService.updateRequest(uploadProductRequestDto);

            HttpStatus status=HttpStatus.OK;
            return  new ResponseEntity<>(status);
        }
        catch (Exception e){
            HttpStatus status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
    }

    @PostMapping("/admin/upload-requests/approve")
    public ResponseEntity<Void> approveRequest(@RequestBody Map<String,String> data){
        try{
            Long requestId=Long.parseLong(data.get("requestId"));
            String username=data.get("username");
            System.out.println(data);

            UploadProductRequestDto uploadProductRequestDto=uploadProductRequestService.findById(requestId);
            UserDto sellerUser=userService.findByUsername(username);

            uploadProductRequestDto.setApproved(true);
            uploadProductRequestService.updateRequest(uploadProductRequestDto);

            ProductDto newProduct=createProduct(uploadProductRequestDto,sellerUser);
            productService.addProduct(newProduct);

            HttpStatus httpStatus=HttpStatus.OK;
            return new ResponseEntity<>(httpStatus);
        }
        catch (Exception e){
            HttpStatus httpStatus=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(httpStatus);
        }

    }

    public ProductDto createProduct(UploadProductRequestDto uploadProductRequestDto,UserDto userDto){
        ProductDto productDto=new ProductDto();
        productDto.setUploaderId(userDto.getId());
        productDto.setCategory(uploadProductRequestDto.getCategory());
        productDto.setName(uploadProductRequestDto.getName());
        productDto.setYear(uploadProductRequestDto.getYear());
        productDto.setPrice(uploadProductRequestDto.getPrice());
        productDto.setImagePath(uploadProductRequestDto.getImagePath());
        productDto.setDescription(uploadProductRequestDto.getDescription());
        productDto.setRequestId(uploadProductRequestDto.getId());
        return productDto;
    }
}
