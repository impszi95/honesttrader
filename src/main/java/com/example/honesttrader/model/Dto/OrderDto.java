package com.example.honesttrader.model.Dto;

import com.example.honesttrader.model.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDto {
    private Long userId;
    private Long securityId;
    private OrderType orderType;
    private int price;
    private int quantity;
}
