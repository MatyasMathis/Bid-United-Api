package com.example.bidunitedapi.bidunitedapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String username;
    private String password;
    private String token;
    private String phoneNumber;
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<UploadProductRequest> uploadProductRequestList;

}
