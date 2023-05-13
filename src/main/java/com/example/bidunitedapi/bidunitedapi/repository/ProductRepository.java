package com.example.bidunitedapi.bidunitedapi.repository;

import com.example.bidunitedapi.bidunitedapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByRequestId(Long id);
    List<Product> getProductsByBuyerId(Long buyerId);
    //List<Product> getProductsByBoughtIsAndUploaderId(boolean bought,Long uploaderId);
}
