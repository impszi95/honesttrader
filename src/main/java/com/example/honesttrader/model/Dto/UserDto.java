package com.example.honesttrader.model.Dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class UserDto {
    private String username;
    private String password;
}
