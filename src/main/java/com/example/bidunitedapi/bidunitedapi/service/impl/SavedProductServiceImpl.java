package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.SavedProductDto;
import com.example.bidunitedapi.bidunitedapi.entity.SavedProduct;
import com.example.bidunitedapi.bidunitedapi.mapper.ProductMapper;
import com.example.bidunitedapi.bidunitedapi.mapper.SavedProductMapper;
import com.example.bidunitedapi.bidunitedapi.repository.SavedProductRepository;
import com.example.bidunitedapi.bidunitedapi.service.SavedProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SavedProductServiceImpl implements SavedProductService {
    @Autowired
    private SavedProductRepository savedProductRepository;
    @Override
    public void addSavedProduct(SavedProductDto savedProductDto) {
        SavedProduct savedProduct= SavedProductMapper.mapToSaved(savedProductDto);
        savedProductRepository.save(savedProduct);
    }

    @Override
    public List<SavedProductDto> getByUser(Long id) {
        List<SavedProduct> list=savedProductRepository.getSavedProductByUserId(id);
        return list.stream().map(SavedProductMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<SavedProductDto> getByUSerAndProduct(Long userId, Long productId) {
        List<SavedProduct> list=savedProductRepository.getSavedProductByUserIdAndProductId(userId,productId);
        return list.stream().map(SavedProductMapper::mapToDto).collect(Collectors.toList());
    }
}
