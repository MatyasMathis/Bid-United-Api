package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class ProductUserDto {
    private String username;
    private int nrOfProducts;
}
