package com.example.bidunitedapi.bidunitedapi.service;

import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.User;

public interface UserService {
    UserDto findByUsername(String username);
    User findById(Long id);

}
