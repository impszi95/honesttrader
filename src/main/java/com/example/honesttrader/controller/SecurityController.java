package com.example.honesttrader.controller;

import com.example.honesttrader.model.Security;
import com.example.honesttrader.model.User;
import com.example.honesttrader.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/securities")
public class SecurityController {

    @Autowired
    SecurityService securityService;

    @PostMapping("/create")
    public ResponseEntity<?> AddSecurity(@RequestBody Security security) {
        return securityService.AddSecurity(security);
    }
    @GetMapping("")
    public ResponseEntity<?> GetSecurities() {
        return securityService.GetAllSecurities();
    }
}
