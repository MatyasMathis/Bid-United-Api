package com.example.bidunitedapi.bidunitedapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "upload_product_requests")
public class UploadProductRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String category;
    private String sport;
    private int year;
    private String description;
    private String imagePath;
    private int price;
    private boolean isApproved;
    private boolean rejected;
    private String uploadDate;
    private LocalDate expireDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
