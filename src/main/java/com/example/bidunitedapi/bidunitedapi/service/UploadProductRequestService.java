package com.example.bidunitedapi.bidunitedapi.service;

import com.example.bidunitedapi.bidunitedapi.dto.UploadProductRequestDto;
import com.example.bidunitedapi.bidunitedapi.dto.UploadRequestOutputDto;

import java.util.List;

public interface UploadProductRequestService {

    void addProductRequest(UploadProductRequestDto uploadProductRequestDto);
    List<UploadProductRequestDto> uploadRequestList();
    List<UploadRequestOutputDto> getAllUnApprovedRequests();
    UploadProductRequestDto findById(Long id);
    void deleteById(Long id);
    void updateRequest(UploadProductRequestDto uploadProductRequestDto);
    List<UploadRequestOutputDto> getRequestsByUser(String username);
    List<UploadRequestOutputDto> getRequestsPendingByUser(String username);
    List<UploadRequestOutputDto> getRequestsDeclinedByUser(String username);
}
