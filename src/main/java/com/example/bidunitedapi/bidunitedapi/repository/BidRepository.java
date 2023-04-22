package com.example.bidunitedapi.bidunitedapi.repository;

import com.example.bidunitedapi.bidunitedapi.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid,Long> {
   List<Bid> getBidByProductId(Long productId);
   void deleteBidByProductId(Long productId);
}
