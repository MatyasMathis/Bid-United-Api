package com.example.bidunitedapi.bidunitedapi.repository;

import com.example.bidunitedapi.bidunitedapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findById(long id);
    List<User> findByEmail(String email);
    List<User> findUsersByUsername(String username);
}
