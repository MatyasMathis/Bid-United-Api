package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.BidDto;
import com.example.bidunitedapi.bidunitedapi.entity.Bid;
import com.example.bidunitedapi.bidunitedapi.mapper.BidMapper;
import com.example.bidunitedapi.bidunitedapi.mapper.SavedProductMapper;
import com.example.bidunitedapi.bidunitedapi.repository.BidRepository;
import com.example.bidunitedapi.bidunitedapi.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidServiceImpl implements BidService {
    @Autowired
    private BidRepository bidRepository;
    @Override
    public void createBid(BidDto bidDto) {
        Bid bid= BidMapper.mapToBid(bidDto);
        bidRepository.save(bid);
    }

    @Override
    public void deleteByProductId(Long productId) {
        bidRepository.deleteBidByProductId(productId);
    }

    @Override
    public List<BidDto> getBidsByProduct(Long productId) {
        List<Bid> bids=bidRepository.getBidByProductId(productId);
        return bids.stream().map(BidMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<BidDto> getAllBids() {
        List<Bid> bids=bidRepository.findAll();
        return bids.stream().map(BidMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<BidDto> getBidsByUser(Long userId) {
        List<Bid> bids=bidRepository.getBidByUserId(userId);
        return bids.stream().map(BidMapper::mapToDto).collect(Collectors.toList());
    }
}
