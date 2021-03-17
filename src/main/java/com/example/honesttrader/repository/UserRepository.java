package com.example.honesttrader.repository;

import com.example.honesttrader.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);
}
