package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.dto.SoldProductDto;
import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.Bid;
import com.example.bidunitedapi.bidunitedapi.entity.Product;
import com.example.bidunitedapi.bidunitedapi.entity.User;
import com.example.bidunitedapi.bidunitedapi.mapper.BidMapper;
import com.example.bidunitedapi.bidunitedapi.mapper.ProductMapper;
import com.example.bidunitedapi.bidunitedapi.repository.BidRepository;
import com.example.bidunitedapi.bidunitedapi.repository.ProductRepository;
import com.example.bidunitedapi.bidunitedapi.repository.UserRepository;
import com.example.bidunitedapi.bidunitedapi.service.ProductService;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BidRepository bidRepository;
    @Override
    public List<ProductDto> getAllProducts() {
        verifyExpiredProducts();
        List<Product> products=productRepository.findAll();
        List<ProductDto> productDtos= products.stream().map(ProductMapper::mapToDto).collect(Collectors.toList());

        for (ProductDto product:productDtos
             ) {

            User user=userRepository.findById(product.getUploaderId()).get();
            product.setPhoneNumber(user.getPhoneNumber());
            product.setEmail(user.getEmail());
        }

        return productDtos;
    }

    @Override
    public List<ProductDto> getAllUnsoldProducts() {
        verifyExpiredProducts();
        List<Product> products=productRepository.findAll();
        List<ProductDto> productDtos= products.stream() .filter(product -> !product.getExpired()).map(ProductMapper::mapToDto).collect(Collectors.toList());

        for (ProductDto product:productDtos
        ) {

            User user=userRepository.findById(product.getUploaderId()).get();
            product.setPhoneNumber(user.getPhoneNumber());
            product.setEmail(user.getEmail());
        }

        return productDtos;
    }

    @Override
    public List<ProductDto> getAllSoldProducts() {
        verifyExpiredProducts();
        List<Product> products=productRepository.findAll();
        List<ProductDto> productDtos= products.stream() .filter(product -> product.isBought()).map(ProductMapper::mapToDto).collect(Collectors.toList());

        for (ProductDto product:productDtos
        ) {

            User user=userRepository.findById(product.getUploaderId()).get();
            product.setPhoneNumber(user.getPhoneNumber());
            product.setEmail(user.getEmail());
        }

        return productDtos;
    }

    public void verifyExpiredProducts(){
        List<Product> products=productRepository.findAll();
        LocalDate currentdate=LocalDate.now();

        for (Product product:products
        ) {
            if(currentdate.isAfter(product.getLimitDate())){
                product.setExpired(true);
                if(!bidRepository.getBidByProductId(product.getId()).isEmpty()){
                    product.setBought(true);
                    List<Bid> bidList= bidRepository.getBidByProductId(product.getId());
                    product.setBuyerId(bidList.get(bidList.size()-1).getUserId());

                    if(product.getValidationCode().equals("-")){
                        Random random = new Random();
                        // Generate a random integer between 0 and 999999
                        int code = random.nextInt(1000000);
                        // Format the integer as a 6-digit string with leading zeros
                        String formattedCode = String.format("%06d", code);
                        product.setValidationCode(formattedCode);
                    }
                }
                else {
                    product.setBought(false);
                    product.setBuyerId(-1L);
                }

                productRepository.save(product);
            }
        }
    }

    @Override
    public void addProduct(ProductDto productDto) {
        Product product=ProductMapper.mapToProductWhenUpload(productDto);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(ProductDto productDto) {
        Product product=ProductMapper.mapToProduct(productDto);
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public ProductDto findByRequestId(Long id) {
        Product product=productRepository.findByRequestId(id);
        return ProductMapper.mapToDto(product);
    }

    @Override
    public ProductDto findById(Long id) {
       Product product=productRepository.findById(id).get();
       return ProductMapper.mapToDto(product);
    }

    @Override
    public List<ProductDto> getCartByUser(Long buyerId) {
        List<Product> products=productRepository.getProductsByBuyerId(buyerId);
        return products.stream().map(ProductMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<SoldProductDto> getSoldProductsBySellerId(Long sellerId) {
        List<Product> products=productRepository.getProductsByUploaderId(sellerId);
        List<Product> soldProducts=new ArrayList<>();
        for (Product product:products) {
            if(product.isBought()){
                soldProducts.add(product);
            }
        }
        List<SoldProductDto> soldProductDtos=soldProducts.stream().map(ProductMapper::mapToSoldDto).collect(Collectors.toList());
        return soldProductDtos;
    }

    @Override
    public List<ProductDto> getProductBySellerid(Long sellerId) {
        List<Product> productList=productRepository.getProductsByUploaderId(sellerId);
        return productList.stream().map(ProductMapper::mapToDto).collect(Collectors.toList());
    }

}