package com.example.honesttrader.service;
import com.example.honesttrader.model.*;
import com.example.honesttrader.model.Dto.OrderDto;
import com.example.honesttrader.repository.OrderRepository;
import com.example.honesttrader.repository.SecurityRepository;
import com.example.honesttrader.repository.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TradeService tradeService;

    @Autowired
    SecurityRepository securityRepository;

    public ResponseEntity<?> CreateOrder(OrderDto orderDto){
        try {
            Long userId = orderDto.getUserId();
            User user = userRepository.findById(userId).orElseThrow(() -> new UserPrincipalNotFoundException("User not found with id: "+userId));
            securityRepository.findById(orderDto.getSecurityId()).orElseThrow(() -> new NotFoundException("Security not exists:"+orderDto.getSecurityId()));

            Order newOrder = new Order(user, orderDto.getSecurityId(), orderDto.getOrderType(), orderDto.getPrice(), orderDto.getQuantity());
            orderRepository.save(newOrder);

            ProcessOrder(newOrder);
            return newOrder.isFullfilled() ? ResponseEntity.ok(tradeService.GetTradesForOrder(newOrder)) : ResponseEntity.ok(newOrder);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    public void ProcessOrder(Order order) {
        for (Order otherOrder : orderRepository.GetUnfullfilledsOrdered()) {
            boolean securityMatches = order.getSecurityId().equals(otherOrder.getSecurityId());
            boolean oneBuyOneSell = (order.getOrderType() == OrderType.BUY && otherOrder.getOrderType() == OrderType.SELL)
                                    || (order.getOrderType() == OrderType.SELL && otherOrder.getOrderType() == OrderType.BUY);

            if (securityMatches && oneBuyOneSell && pricesCompatible(order, otherOrder)){
                tradeService.createTrade(order, otherOrder);
                if(order.isFullfilled()){
                    break;
                }
            }
        }
    }

    private boolean pricesCompatible(Order order1, Order order2) {
        Order buy = order1.getOrderType() == OrderType.BUY ? order1 : order2;
        Order sell = order1.getOrderType() == OrderType.SELL ? order1 : order2;
        return buy.getPrice() >= sell.getPrice();
    }

}
