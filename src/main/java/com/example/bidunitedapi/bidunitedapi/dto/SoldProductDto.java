package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class SoldProductDto {
    private Long id;
    private String name;
    private int startingPrice;
    private int price;
    private String imagePath;
    private String validationCode;
    private Long buyerId;
    private String buyerEmail;
    private String buyerPhone;
}
