package com.example.honesttrader.service;

import com.example.honesttrader.model.Order;
import com.example.honesttrader.model.OrderType;
import com.example.honesttrader.model.Trade;
import com.example.honesttrader.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    @Autowired
    TradeRepository tradeRepository;

    public void createTrade(Order order1, Order order2) {
        Order buy = order1.getOrderType() == OrderType.BUY ? order1 : order2;
        Order sell = order1.getOrderType() == OrderType.SELL ? order1 : order2;
        int quantity = Math.min(buy.getRealQuantity(),
                sell.getRealQuantity());

        Trade trade = new Trade(sell.getId(),buy.getId(),sell.getPrice(),quantity);
        buy.applyTrade(trade);
        sell.applyTrade(trade);

        tradeRepository.save(trade);
    }

    public List<Trade> GetTradesForOrder(Order order){
        return order.getOrderType() == OrderType.BUY ? tradeRepository.findAllByBuyOrderId(order.getId())
                                                                    : tradeRepository.findAllBySellOrderId(order.getId());
    }
}
