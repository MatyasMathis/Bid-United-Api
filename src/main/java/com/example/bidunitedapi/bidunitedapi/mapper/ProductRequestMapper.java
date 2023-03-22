package com.example.bidunitedapi.bidunitedapi.mapper;

import com.example.bidunitedapi.bidunitedapi.dto.UploadProductRequestDto;
import com.example.bidunitedapi.bidunitedapi.dto.UploadRequestOutputDto;
import com.example.bidunitedapi.bidunitedapi.entity.UploadProductRequest;
import com.example.bidunitedapi.bidunitedapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class ProductRequestMapper {

    public static UploadProductRequest mapToRequest(UploadProductRequestDto uploadProductRequestDto){
        UploadProductRequest uploadProductRequest=UploadProductRequest.builder()
                .id(uploadProductRequestDto.getId())
                .uploadDate(uploadProductRequestDto.getUploadDate())
                .name(uploadProductRequestDto.getName())
                .price(uploadProductRequestDto.getPrice())
                .year(uploadProductRequestDto.getYear())
                .description(uploadProductRequestDto.getDescription())
                .category(uploadProductRequestDto.getCategory())
                .imagePath(uploadProductRequestDto.getImagePath())
                .isApproved(uploadProductRequestDto.isApproved())
                .build();
        return uploadProductRequest;
    }
    public static UploadProductRequestDto mapToDto(UploadProductRequest uploadProductRequest){
        UploadProductRequestDto uploadProductRequestDto=UploadProductRequestDto.builder()
                .uploadDate(uploadProductRequest.getUploadDate())
                .isApproved(uploadProductRequest.isApproved())
                .id(uploadProductRequest.getId())
                .price(uploadProductRequest.getPrice())
                .year(uploadProductRequest.getYear())
                .category(uploadProductRequest.getCategory())
                .description(uploadProductRequest.getDescription())
                .imagePath(uploadProductRequest.getImagePath())
                .userId(uploadProductRequest.getUser().getId())
                .name(uploadProductRequest.getName())
                .build();
        return uploadProductRequestDto;
    }
    public static UploadRequestOutputDto mapToOutputDto(UploadProductRequest uploadProductRequest){
        UploadRequestOutputDto uploadRequestOutputDto=UploadRequestOutputDto.builder()
                .id(uploadProductRequest.getId())
                .uploadDate(uploadProductRequest.getUploadDate())
                .category(uploadProductRequest.getCategory())
                .price(uploadProductRequest.getPrice())
                .year(uploadProductRequest.getYear())
                .imagePath(uploadProductRequest.getImagePath())
                .name(uploadProductRequest.getName())
                .description(uploadProductRequest.getDescription())
                .username(uploadProductRequest.getUser().getUsername())
                .build();
        return uploadRequestOutputDto;
    }
}
