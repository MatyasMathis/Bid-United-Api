package com.example.bidunitedapi.bidunitedapi.repository;

import com.example.bidunitedapi.bidunitedapi.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid,Long> {
}
