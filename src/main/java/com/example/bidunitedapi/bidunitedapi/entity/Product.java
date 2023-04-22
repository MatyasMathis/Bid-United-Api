package com.example.bidunitedapi.bidunitedapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    private int year;
    private String description;
    private String imagePath;
    private int price;
    private Long uploaderId;
    private Long requestId;
    private boolean isBought;
    private LocalDate limitDate;
    private Long buyerId;
    private Boolean expired;
}
