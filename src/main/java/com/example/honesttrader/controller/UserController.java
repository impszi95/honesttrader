package com.example.honesttrader.controller;

import com.example.honesttrader.model.Dto.UserDto;
import com.example.honesttrader.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> RegisterUser(@RequestBody UserDto userDto) {
        return userService.RegisterUser(userDto);
    }
    @GetMapping("")
    public ResponseEntity<?> GetUsers() {
        return userService.GetAllUsers();
    }
}
