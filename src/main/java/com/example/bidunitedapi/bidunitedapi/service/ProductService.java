package com.example.bidunitedapi.bidunitedapi.service;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.dto.SoldProductDto;
import com.example.bidunitedapi.bidunitedapi.entity.Product;
import com.example.bidunitedapi.bidunitedapi.service.impl.ProductServiceImpl;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    List<ProductDto> getAllUnsoldProducts();
    List<ProductDto> getAllSoldProducts();
    void addProduct(ProductDto productDto);
    void updateProduct(ProductDto productDto);
    void deleteById(Long id);
    ProductDto findByRequestId(Long id);
    ProductDto findById(Long id);
    List<ProductDto> getCartByUser(Long buyerId);
   // List<ProductDto> getSoldProducts(Long sellerId);
    List<SoldProductDto> getSoldProductsBySellerId(Long sellerId);
    List<ProductDto> getProductBySellerid(Long sellerId);
}
