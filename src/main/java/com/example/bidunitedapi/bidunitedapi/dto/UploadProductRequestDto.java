package com.example.bidunitedapi.bidunitedapi.dto;

import com.example.bidunitedapi.bidunitedapi.entity.User;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class UploadProductRequestDto {
    private long id;
    private String name;
    private String category;
    private int year;
    private String description;
    private String imagePath;
    private int price;
    private boolean isApproved;
    private String uploadDate;
    private long userId;
    public UploadProductRequestDto(String name, String category, int year, String description, String imagePath, int price, boolean isApproved, String uploadDate, long userId) {
        this.name = name;
        this.category = category;
        this.year = year;
        this.description = description;
        this.imagePath = imagePath;
        this.price = price;
        this.isApproved = isApproved;
        this.uploadDate = uploadDate;
        this.userId = userId;
    }
}
