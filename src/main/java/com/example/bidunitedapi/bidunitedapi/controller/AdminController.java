package com.example.bidunitedapi.bidunitedapi.controller;

import com.example.bidunitedapi.bidunitedapi.dto.*;
import com.example.bidunitedapi.bidunitedapi.service.BidService;
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

import java.util.ArrayList;
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
    @Autowired
    BidService bidService;

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
        productDto.setExpireDate(uploadProductRequestDto.getExpireDate().toString());
        productDto.setExpired(false);
        productDto.setBought(false);
        productDto.setValidationCode("-");
        return productDto;
    }

    //Data for Charts
    @GetMapping("/admin/stats/{productId}")
    public List<ProductLineChartDto> getBidsByProduct(@PathVariable("productId") Long productId){
        List<BidDto> bidList=bidService.getBidsByProduct(productId);
        List<ProductLineChartDto> statList=new ArrayList<>();
        for (BidDto listItem:bidList
             ) {
            ProductLineChartDto stat=new ProductLineChartDto();
            stat.setAmount(listItem.getAmount());
            stat.setUserId(listItem.getUserId());
            stat.setDate(listItem.getCurrentDate().toString());

            statList.add(stat);
        }
        return statList;
    }

    @GetMapping("/admin/getbids")
    public List<ProductLineChartDto> getAllBids(){
        List<BidDto> bidList=bidService.getAllBids();
        List<ProductLineChartDto> statList=new ArrayList<>();
        for (BidDto listItem:bidList
        ) {
            ProductLineChartDto stat=new ProductLineChartDto();
            stat.setAmount(listItem.getAmount());
            stat.setUserId(listItem.getUserId());
            stat.setDate(listItem.getCurrentDate().toString());

            statList.add(stat);
        }
        return statList;
    }

}
