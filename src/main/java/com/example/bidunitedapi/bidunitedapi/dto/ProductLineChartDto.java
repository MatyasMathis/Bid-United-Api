package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter

public class ProductLineChartDto {
    private Integer amount;
    private String date;
    private Long userId;

}
