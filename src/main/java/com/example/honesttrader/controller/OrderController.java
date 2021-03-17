package com.example.honesttrader.controller;

import com.example.honesttrader.model.Dto.OrderDto;
import com.example.honesttrader.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<?> CreateOrder(@RequestBody OrderDto orderDto){
        return orderService.CreateOrder(orderDto);
    }
}
