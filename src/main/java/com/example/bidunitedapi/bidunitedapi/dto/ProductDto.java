package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class ProductDto {
    private String id;
    private String name;
    private String category;
    private int year;
    private String description;
    private String imagePath;
    private int price;
    private Long uploaderId;
    private Long requestId;
    private String phoneNumber;
    private String email;
    private String expireDate;
    private Boolean expired;
    private boolean isBought;
    private String validationCode;
}
