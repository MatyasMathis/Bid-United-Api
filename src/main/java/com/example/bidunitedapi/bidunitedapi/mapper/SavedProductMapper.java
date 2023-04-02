package com.example.bidunitedapi.bidunitedapi.mapper;

import com.example.bidunitedapi.bidunitedapi.dto.SavedProductDto;
import com.example.bidunitedapi.bidunitedapi.entity.SavedProduct;

public class SavedProductMapper {
    public static SavedProductDto mapToDto(SavedProduct savedProduct){
        SavedProductDto savedProductDto=SavedProductDto.builder()
                .productId(savedProduct.getProductId())
                .id(savedProduct.getId())
                .userId(savedProduct.getUserId())
                .build();
        return savedProductDto;
    }

    public static SavedProduct mapToSaved(SavedProductDto savedProductDto){
        SavedProduct savedProduct= SavedProduct.builder()
                .productId(savedProductDto.getProductId())
                .userId(savedProductDto.getUserId())
                .build();
        return savedProduct;
    }
}
