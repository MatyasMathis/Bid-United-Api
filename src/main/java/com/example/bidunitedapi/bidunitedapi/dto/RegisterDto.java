package com.example.bidunitedapi.bidunitedapi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Getter
@Setter
public class RegisterDto {
    private String email;
    private String phone;
    private String username;
    private String password;
    private String token;
}
