package com.example.bidunitedapi.bidunitedapi.repository;

import com.example.bidunitedapi.bidunitedapi.entity.UploadProductRequest;
import com.example.bidunitedapi.bidunitedapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadProductRequestRepository extends JpaRepository<UploadProductRequest,Long> {
    void deleteById(Long id);
    List<UploadProductRequest> findByUser(User user);
}
