package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.Product;
import com.example.bidunitedapi.bidunitedapi.entity.User;
import com.example.bidunitedapi.bidunitedapi.mapper.ProductMapper;
import com.example.bidunitedapi.bidunitedapi.repository.ProductRepository;
import com.example.bidunitedapi.bidunitedapi.repository.UserRepository;
import com.example.bidunitedapi.bidunitedapi.service.ProductService;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<ProductDto> getAllProducts() {
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
    public void addProduct(ProductDto productDto) {
        Product product=ProductMapper.mapToProduct(productDto);
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
}