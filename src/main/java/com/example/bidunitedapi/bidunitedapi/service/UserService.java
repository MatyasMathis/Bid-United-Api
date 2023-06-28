package com.example.bidunitedapi.bidunitedapi.service;

import com.example.bidunitedapi.bidunitedapi.dto.RegisterDto;
import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.User;

import java.util.List;

public interface UserService {
    UserDto findByUsername(String username);
    List<UserDto> findByEmail(String email);
    List<UserDto> findUsersByUsername(String username);
    User findById(Long id);
    List<User> getAll();
    List<UserDto> getAllUsers();
    void registerNewUser(RegisterDto newUser);

}
