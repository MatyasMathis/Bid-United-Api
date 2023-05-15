package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.User;
import com.example.bidunitedapi.bidunitedapi.mapper.UserMapper;
import com.example.bidunitedapi.bidunitedapi.repository.UserRepository;
import com.example.bidunitedapi.bidunitedapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDto findByUsername(String username) {
        User user=userRepository.findByUsername(username);
        return UserMapper.mapToDto(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
