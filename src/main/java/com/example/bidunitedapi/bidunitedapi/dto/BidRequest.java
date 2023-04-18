package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.Data;

@Data
public class BidRequest {
    private Long userId;
    private Long productId;
    private Integer amount;
}
