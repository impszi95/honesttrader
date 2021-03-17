package com.example.honesttrader.repository;

import com.example.honesttrader.model.Order;
import com.example.honesttrader.model.OrderType;
import com.example.honesttrader.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    @Query(value = "SELECT * FROM orders WHERE fullfilled = 0 ORDER BY price", nativeQuery = true)
    List<Order> GetUnfullfilledsOrdered();

    Order findByOrderType(OrderType orderType);
}
