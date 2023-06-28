package com.example.bidunitedapi.bidunitedapi.controller;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin("http://localhost:4200/")
public class ProductController {
    @Autowired
    private ProductService productService;


    @GetMapping("/products")
    public List<ProductDto> getAllProducts(){
        return productService.getAllUnsoldProducts();
    }
    @GetMapping("/products/each")
    public List<ProductDto> getEachProduct(){
        return productService.getAllProducts();
    }

    @GetMapping("/products/unsold")
    public List<ProductDto> getAllUnsoldProducts(){
        return productService.getAllUnsoldProducts();
    }

    @GetMapping("/products/sold")
    public List<ProductDto> getAllSoldProducts(){
        return productService.getAllSoldProducts();
    }
}
