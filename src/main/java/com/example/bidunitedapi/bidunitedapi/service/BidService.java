package com.example.bidunitedapi.bidunitedapi.service;

import com.example.bidunitedapi.bidunitedapi.dto.BidDto;

import java.util.List;

public interface BidService {
    void createBid(BidDto bidDto);
    void deleteByProductId(Long productId);

    List<BidDto> getBidsByProduct(Long productId);
    List<BidDto> getAllBids();
    List<BidDto> getBidsByUser(Long userId);
}
