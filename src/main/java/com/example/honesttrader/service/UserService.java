package com.example.honesttrader.service;

import com.example.honesttrader.model.Dto.UserDto;
import com.example.honesttrader.model.User;
import com.example.honesttrader.repository.OrderRepository;
import com.example.honesttrader.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> RegisterUser(UserDto userDto){
        try {
            boolean userExists = userRepository.existsByUsername(userDto.getUsername());
            if(userExists){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            String hashedPassword = String.valueOf(userDto.getPassword().hashCode());
            User newUser = new User(userDto.getUsername(), hashedPassword);
            userRepository.save(newUser);
            return ResponseEntity.ok(newUser);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<?> GetAllUsers(){
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
