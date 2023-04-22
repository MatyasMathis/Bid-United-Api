package com.example.bidunitedapi.bidunitedapi.mapper;

import com.example.bidunitedapi.bidunitedapi.dto.ProductDto;
import com.example.bidunitedapi.bidunitedapi.entity.Product;

import java.time.LocalDate;

public class ProductMapper {
    public static ProductDto mapToDto(Product product){
        ProductDto productDto=ProductDto.builder()
                .name(product.getName())
                .id(product.getId().toString())
                .category(product.getCategory())
                .description(product.getDescription())
                .year(product.getYear())
                .imagePath(product.getImagePath())
                .price(product.getPrice())
                .uploaderId(product.getUploaderId())
                .requestId(product.getRequestId())
                .expireDate(product.getLimitDate().toString())
                .expired(product.getExpired())
                .isBought(product.isBought())
                .build();
        return productDto;
    }

    public static Product mapToProduct(ProductDto productDto){
        Product product=Product.builder()
                .id(Long.parseLong(productDto.getId()))
                .category(productDto.getCategory())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .imagePath(productDto.getImagePath())
                .price(productDto.getPrice())
                .year(productDto.getYear())
                .uploaderId(productDto.getUploaderId())
                .requestId(productDto.getRequestId())
                .limitDate(LocalDate.parse(productDto.getExpireDate()))
                .expired(productDto.getExpired())
                .isBought(productDto.isBought())
                .build();
        return product;
    }

    public static Product mapToProductWhenUpload(ProductDto productDto){
        Product product=Product.builder()
                .category(productDto.getCategory())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .imagePath(productDto.getImagePath())
                .price(productDto.getPrice())
                .year(productDto.getYear())
                .uploaderId(productDto.getUploaderId())
                .requestId(productDto.getRequestId())
                .limitDate(LocalDate.parse(productDto.getExpireDate()))
                .expired(productDto.getExpired())
                .isBought(productDto.isBought())
                .build();
        return product;
    }
}
