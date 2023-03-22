package com.example.bidunitedapi.bidunitedapi.mapper;

import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.User;

public class UserMapper {
    public static UserDto mapToDto(User user){
        UserDto userDto=UserDto.builder()
                .Id(user.getId())
                .password(user.getPassword())
                .token(user.getToken())
                .username(user.getUsername())
                .build();
        return userDto;
    }
}
