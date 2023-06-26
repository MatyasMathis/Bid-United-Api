package com.example.bidunitedapi.bidunitedapi.controller;

import com.example.bidunitedapi.bidunitedapi.dto.RegisterDto;
import com.example.bidunitedapi.bidunitedapi.dto.UploadRequestOutputDto;
import com.example.bidunitedapi.bidunitedapi.dto.UserDto;
import com.example.bidunitedapi.bidunitedapi.entity.User;
import com.example.bidunitedapi.bidunitedapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:4200/")
public class LoginController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        System.out.println(username+password);

        UserDto user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(user, status);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterDto newUser) {

        try {
            if(!userService.findByEmail(newUser.getEmail()).isEmpty() || !userService.findUsersByUsername(newUser.getUsername()).isEmpty()){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            else{
                userService.registerNewUser(newUser);
            }
            return new ResponseEntity(newUser,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

}
