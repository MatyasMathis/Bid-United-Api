package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class UserBidHistoryDto {
    private Long productId;
    private String productName;
    private int amount;
    private String status;
    private String imageUrl;
    private String expireDate;
}
