package com.example.bidunitedapi.bidunitedapi.service;

import com.example.bidunitedapi.bidunitedapi.dto.SavedProductDto;

import java.util.List;

public interface SavedProductService {
    void addSavedProduct(SavedProductDto savedProductDto);
    List<SavedProductDto> getByUser(Long id);

    List<SavedProductDto> getByUSerAndProduct(Long userId,Long productId);

    void unsaveProduct(SavedProductDto savedProductDto);
    void deleteByProductId(Long productId);
}
