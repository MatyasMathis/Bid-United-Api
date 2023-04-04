package com.example.bidunitedapi.bidunitedapi.service;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.entity.Product;
import com.example.bidunitedapi.bidunitedapi.service.impl.ProductServiceImpl;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllProducts();
    void addProduct(ProductDto productDto);
    void deleteById(Long id);
    ProductDto findByRequestId(Long id);
    ProductDto findById(Long id);

}
