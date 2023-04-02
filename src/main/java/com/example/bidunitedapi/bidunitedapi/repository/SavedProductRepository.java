package com.example.bidunitedapi.bidunitedapi.repository;

import com.example.bidunitedapi.bidunitedapi.entity.SavedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedProductRepository extends JpaRepository<SavedProduct,Long> {
    List<SavedProduct> getSavedProductByUserId(Long id);
}
