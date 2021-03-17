package com.example.honesttrader.controller;

import com.example.honesttrader.model.Dto.UserDto;
import com.example.honesttrader.model.User;
import com.example.honesttrader.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @ValueSource(strings = { "Paper", "Diamond", "Elon Musk" })
    void TestRegisterUser(String username) throws Exception{
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword("password");

        mockMvc.perform(post("/users/register", 42L)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk());

        User user = userRepository.findByUsername(username);
        assertEquals(user.getUsername(),username);
    }
}