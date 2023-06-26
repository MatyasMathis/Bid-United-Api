package com.example.bidunitedapi.bidunitedapi.service.impl;

import com.example.bidunitedapi.bidunitedapi.dto.RegisterDto;
import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.User;
import com.example.bidunitedapi.bidunitedapi.mapper.ProductMapper;
import com.example.bidunitedapi.bidunitedapi.mapper.UserMapper;
import com.example.bidunitedapi.bidunitedapi.repository.UserRepository;
import com.example.bidunitedapi.bidunitedapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserDto> findByEmail(String email) {
        List<User> user=userRepository.findByEmail(email);
        return user.stream().map(UserMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findUsersByUsername(String username) {
        List<User> user=userRepository.findUsersByUsername(username);
        return user.stream().map(UserMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void registerNewUser(RegisterDto newUser) {
        User user=new User();
        user.setEmail(newUser.getEmail());
        user.setPassword(newUser.getPassword());
        user.setToken(newUser.getToken());
        user.setPhoneNumber(newUser.getPhone());
        user.setUsername(newUser.getUsername());

        userRepository.save(user);
    }
}
