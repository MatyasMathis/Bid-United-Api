package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class BidDto {
    private Long id;
    private Long userId;
    private Long productId;
   private LocalDate currentDate;
    private int amount;
}
