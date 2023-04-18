package com.example.bidunitedapi.bidunitedapi.controller;

import com.example.bidunitedapi.bidunitedapi.dto.*;
import com.example.bidunitedapi.bidunitedapi.entity.Product;
import com.example.bidunitedapi.bidunitedapi.service.BidService;
import com.example.bidunitedapi.bidunitedapi.service.ProductService;
import com.example.bidunitedapi.bidunitedapi.service.SavedProductService;
import com.example.bidunitedapi.bidunitedapi.service.UploadProductRequestService;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private BidService bidService;

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
            if(savedProductService.getByUSerAndProduct(credentials.get("userId"), credentials.get("productId")).isEmpty()){

                SavedProductDto savedProductDto=new SavedProductDto();
                savedProductDto.setProductId(credentials.get("productId"));
                savedProductDto.setUserId(credentials.get("userId"));

                savedProductService.addSavedProduct(savedProductDto);

                HttpStatus status=HttpStatus.OK;
                return  new ResponseEntity<>(status);
            }
            else {
                HttpStatus status=HttpStatus.CONFLICT;
                return  new ResponseEntity<>(status);
            }

        }
        catch (Exception e){
            HttpStatus status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
    }

    @PostMapping("/user/unsave")
    public ResponseEntity<Void> unsaveItem(@RequestBody Map<String, Long> credentials){
        try {
            if(!savedProductService.getByUSerAndProduct(credentials.get("userId"), credentials.get("productId")).isEmpty()){
                SavedProductDto savedProductDto=savedProductService.getByUSerAndProduct(credentials.get("userId"), credentials.get("productId")).get(0);
                savedProductService.unsaveProduct(savedProductDto);

                HttpStatus status=HttpStatus.OK;
                return  new ResponseEntity<>(status);
            }
            else {
                HttpStatus status=HttpStatus.CONFLICT;
                return  new ResponseEntity<>(status);
            }

        }
        catch (Exception e){
            HttpStatus status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
    }

    @GetMapping("/user/saved/{id}")
    public ResponseEntity<List<ProductDto>> getAllRequests(@PathVariable("id") Long userId){
        try{
            HttpStatus status = HttpStatus.OK;
            List<SavedProductDto> list=savedProductService.getByUser(userId);
            List<ProductDto> productList=new ArrayList<>();

            for (SavedProductDto savedProduct:list
                 ) {
                ProductDto productDto=productService.findById(savedProduct.getProductId());
                productList.add(productDto);

            }
            return new ResponseEntity<>(productList,status);
        }
        catch (Exception e)
        {
            HttpStatus status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }

    }

    @PostMapping("/user/issaved")
    public ResponseEntity<Boolean> isSaved(@RequestBody Map<String, Long> credentials){
        try {
            if(savedProductService.getByUSerAndProduct(credentials.get("userId"), credentials.get("productId")).isEmpty()){
                return new ResponseEntity<Boolean>(false,HttpStatus.OK);
            }
            return new ResponseEntity<Boolean>(true,HttpStatus.OK);

        }
        catch (Exception e){
            HttpStatus status=HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
    }

    ///Placing and Reading bids
    @PostMapping("/user/newBid")
    public ResponseEntity<Void> placeNewBid(@RequestBody BidRequest bidRequest) {
        try {
            BidDto newBid = new BidDto();
            newBid.setAmount(bidRequest.getAmount());
            LocalDate currentDate = LocalDate.now();
            newBid.setCurrentDate(currentDate);
            newBid.setUserId(bidRequest.getUserId());
            newBid.setProductId(bidRequest.getProductId());

            ProductDto product = productService.findById(bidRequest.getProductId());
            product.setPrice(bidRequest.getAmount());
            productService.updateProduct(product);

            bidService.createBid(newBid);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }
    }


}
