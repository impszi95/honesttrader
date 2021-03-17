package com.example.honesttrader.repository;

import com.example.honesttrader.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeRepository  extends JpaRepository<Trade, Long> {
    List<Trade> findAllBySellOrderId(Long sellOrderId);
    List<Trade> findAllByBuyOrderId(Long buyOrderId);
}
