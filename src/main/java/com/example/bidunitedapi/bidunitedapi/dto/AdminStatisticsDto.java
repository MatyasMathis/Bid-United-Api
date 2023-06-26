package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class AdminStatisticsDto {
    private int totalUsers;
    private int totalProducts;
    private float maxWinOverProduct;
    private List<ProductUserDto> productPerUser;
    private List<ProductStatusDto> productStatus;
    private int nrOfBids;
    private ProductDto highestSoldProduct;

}
