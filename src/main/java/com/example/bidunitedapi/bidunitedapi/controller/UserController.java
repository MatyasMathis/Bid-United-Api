package com.example.bidunitedapi.bidunitedapi.controller;

import com.example.bidunitedapi.bidunitedapi.dto.*;
import com.example.bidunitedapi.bidunitedapi.entity.Product;
import com.example.bidunitedapi.bidunitedapi.entity.User;
import com.example.bidunitedapi.bidunitedapi.service.*;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.*;
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
    @Autowired
    private UserService userService;

    @PostMapping("/user/upload-request")
    public ResponseEntity<Void> addUploadRequest(@RequestParam("file") MultipartFile file, @RequestParam Map<String, String> uploadRequest) throws IOException {
        System.out.println("Received upload request with parameters:"+ uploadRequest);
        String name=uploadRequest.get("name");
        String category=uploadRequest.get("category");
        int year=Integer.parseInt(uploadRequest.get("year"));
        String description=uploadRequest.get("description");
        String imagePath=saveFile(file);
        int price =Integer.parseInt(uploadRequest.get("price"));
        LocalDate date=LocalDate.now();
        String uploadDate=date.toString();
        long userId=Long.parseLong(uploadRequest.get("userId"));
        int plusDays=Integer.parseInt(uploadRequest.get("days"));
        LocalDate expireDate=LocalDate.now().plusDays(plusDays);
        String sport=uploadRequest.get("sport");

        UploadProductRequestDto uploadProductRequestDto=new UploadProductRequestDto(name,category,year,description,imagePath,price,false,uploadDate,userId,expireDate,sport);
        try {
            uploadProductRequestService.addProductRequest(uploadProductRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "C:/Users/User/OneDrive/Desktop/Facultate/Licenta/frontend/bid-commercial-app/src/assets/images/" + fileName; // Remove the leading slash ("/") for relative path

        Path destPath = Paths.get(filePath); // Use Paths.get() instead of Path.of() for compatibility

        Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);

        return "./assets/images/"+ fileName;
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
                if(!productDto.getExpired() && !productDto.isBought()){
                    productList.add(productDto);
                }

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

    @GetMapping("/user/bids/{userId}")
    public ResponseEntity<List<UserBidHistoryDto>> bidHistoryForUser(@PathVariable("userId")String userId){
        try {
          List<BidDto> userBidList=bidService.getBidsByUser(Long.parseLong(userId));
          List<UserBidHistoryDto> bidHistoryDtos=new ArrayList<>();
          List<ProductDto> productDtoList=productService.getAllProducts();
            for (BidDto bidDto:userBidList
                 ) {
                UserBidHistoryDto historyDto=new UserBidHistoryDto();
                historyDto.setAmount(bidDto.getAmount());
                historyDto.setProductId(bidDto.getProductId());
                ProductDto matchingProduct = productDtoList.stream()
                        .filter(obj -> obj.getId().equals(bidDto.getProductId().toString()))
                        .findFirst()
                        .orElse(null);
                historyDto.setExpireDate(matchingProduct.getExpireDate());
                historyDto.setProductName(matchingProduct.getName());
                historyDto.setImageUrl(matchingProduct.getImagePath());

                if(matchingProduct.isBought() || matchingProduct.getExpired()){
                    historyDto.setStatus("Expired");
                }
                else {
                    List<BidDto> bidListByProduct=bidService.getBidsByProduct(historyDto.getProductId());
                    if(bidListByProduct.get(bidListByProduct.size()-1).equals(bidDto)){
                        historyDto.setStatus("Bid is on");
                    }
                    else {
                        historyDto.setStatus("Overbidden");
                    }
                }

                bidHistoryDtos.add(historyDto);
            }
            Collections.reverse(bidHistoryDtos);
            return new ResponseEntity<>(bidHistoryDtos,HttpStatus.OK);

        } catch (Exception e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }
    }

    @GetMapping("/user/cart/{buyerId}")
    public  ResponseEntity<List<ProductDto>> cart(@PathVariable("buyerId")String buyerId){
        try {
            List<ProductDto> productDtoList=productService.getCartByUser(Long.parseLong(buyerId));
            for (ProductDto product:productDtoList
                 ) {
                User seller=userService.findById(product.getUploaderId());
                product.setEmail(seller.getEmail());
                product.setPhoneNumber(seller.getPhoneNumber());
            }
            return new ResponseEntity<>(productDtoList,HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }
    }

    @GetMapping("/user/sold/{sellerId}")
    public  ResponseEntity<List<ProductDto>> getSoldItems(@PathVariable("sellerId")String sellerId){
        try {
            List<SoldProductDto> soldProductDtoList=productService.getSoldProductsBySellerId(Long.parseLong(sellerId));
            for (SoldProductDto product:soldProductDtoList
                 ) {
                User user=userService.findById(product.getBuyerId());
                product.setBuyerEmail(user.getEmail());
                product.setBuyerPhone(user.getPhoneNumber());
            }

            return new ResponseEntity(soldProductDtoList,HttpStatus.OK);
        } catch (Exception e) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(status);
        }
    }
}
