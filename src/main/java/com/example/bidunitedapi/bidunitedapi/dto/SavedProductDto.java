package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class SavedProductDto {
    private Long id;
    private Long userId;
    private Long productId;
}
