package com.example.bidunitedapi.bidunitedapi.mapper;

import com.example.bidunitedapi.bidunitedapi.dto.BidDto;
import com.example.bidunitedapi.bidunitedapi.entity.Bid;

public class BidMapper {
    public static BidDto mapToDto(Bid bid){
        BidDto bidDto=BidDto.builder()
                .userId(bid.getUserId())
                .currentDate(bid.getBidDate())
                .amount(bid.getAmount())
                .productId(bid.getProductId())
                .id(bid.getId())
                .build();
        return bidDto;
    }

    public static Bid mapToBid(BidDto bidDto) {
        Bid bid = Bid.builder()
                .id(bidDto.getId())
                .productId(bidDto.getProductId()).
                userId(bidDto.getUserId()).
                amount(bidDto.getAmount()).
                bidDate(bidDto.getCurrentDate()).
                build();
        return bid;
    }
}
