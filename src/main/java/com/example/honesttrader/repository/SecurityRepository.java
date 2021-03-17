package com.example.honesttrader.repository;

import com.example.honesttrader.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityRepository  extends JpaRepository<Security, Long> {
    Boolean existsByName(String name);
}
