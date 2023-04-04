package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.entity.Product;
import com.example.bidunitedapi.bidunitedapi.mapper.ProductMapper;
import com.example.bidunitedapi.bidunitedapi.repository.ProductRepository;
import com.example.bidunitedapi.bidunitedapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products=productRepository.findAll();
        return products.stream().map(ProductMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void addProduct(ProductDto productDto) {
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
}