package com.example.honesttrader.service;

import com.example.honesttrader.model.Security;
import com.example.honesttrader.model.User;
import com.example.honesttrader.repository.SecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecurityService {
    @Autowired
    SecurityRepository securityRepository;

    public ResponseEntity<?> AddSecurity(Security security){
        try {
            boolean securityExists = securityRepository.existsByName(security.getName());
            if(securityExists){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
           Security newSecurity  = securityRepository.save(security);
            return ResponseEntity.ok(newSecurity);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<?> GetAllSecurities() {
        try {
            List<Security> securities = securityRepository.findAll();
            return ResponseEntity.ok(securities);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
