package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.BidDto;
import com.example.bidunitedapi.bidunitedapi.entity.Bid;
import com.example.bidunitedapi.bidunitedapi.mapper.BidMapper;
import com.example.bidunitedapi.bidunitedapi.repository.BidRepository;
import com.example.bidunitedapi.bidunitedapi.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    private BidRepository bidRepository;
    @Override
    public void createBid(BidDto bidDto) {
        Bid bid= BidMapper.mapToBid(bidDto);
        bidRepository.save(bid);
    }
}
