package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class UploadRequestOutputDto {
    private long id;
    private String name;
    private String category;
    private int year;
    private String description;
    private String imagePath;
    private int price;
    private String uploadDate;
    private String username;
    private String expireDate;
}
