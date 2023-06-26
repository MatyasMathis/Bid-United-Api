package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class ProductStatusDto {
    private String status;
    private int nrOfProducts;
}
