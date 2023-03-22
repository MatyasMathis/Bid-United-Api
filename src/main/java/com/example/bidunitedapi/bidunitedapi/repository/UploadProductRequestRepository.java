package com.example.bidunitedapi.bidunitedapi.repository;

import com.example.bidunitedapi.bidunitedapi.entity.UploadProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadProductRequestRepository extends JpaRepository<UploadProductRequest,Long> {
    void deleteById(Long id);
}
