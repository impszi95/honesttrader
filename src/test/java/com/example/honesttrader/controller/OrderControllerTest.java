package com.example.honesttrader.controller;

import com.example.honesttrader.model.*;
import com.example.honesttrader.model.Dto.OrderDto;
import com.example.honesttrader.model.Dto.UserDto;
import com.example.honesttrader.repository.OrderRepository;
import com.example.honesttrader.repository.SecurityRepository;
import com.example.honesttrader.repository.TradeRepository;
import com.example.honesttrader.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SecurityRepository securityRepository;

    public static TradeTestCase[] SimpleTradeTestCases() {
        TradeTestCase ttc1 = new TradeTestCase();
        ttc1.securities.add(new Security("WSB"));
        ttc1.users.add(new User("Paper", "password"));
        ttc1.users.add(new User("Diamond", "password"));
        ttc1.orders.add(new OrderDto(1L, 1L, OrderType.BUY, 101, 50));
        ttc1.orders.add(new OrderDto(2L, 1L, OrderType.SELL, 100, 100));
        ttc1.trades.add(new Trade(2L, 1L, 100, 50));

        TradeTestCase ttc2 = new TradeTestCase();
        ttc2.securities.add(new Security("WSB"));
        ttc2.users.add(new User("Paper", "password"));
        ttc2.users.add(new User("Diamond", "password"));
        ttc2.orders.add(new OrderDto(1L, 1L, OrderType.BUY, 100, 100));
        ttc2.orders.add(new OrderDto(2L, 1L, OrderType.SELL, 100, 100));
        ttc2.trades.add(new Trade(2L, 1L, 100, 100));

        TradeTestCase ttc3 = new TradeTestCase();
        ttc3.securities.add(new Security("WSB"));
        ttc3.users.add(new User("Paper", "password"));
        ttc3.users.add(new User("Diamond", "password"));
        ttc3.orders.add(new OrderDto(2L, 1L, OrderType.BUY, 200, 50));
        ttc3.orders.add(new OrderDto(1L, 1L, OrderType.SELL, 100, 100));
        ttc3.trades.add(new Trade(1L, 2L, 100, 50));

        return new TradeTestCase[]{ttc1, ttc2, ttc3};
    }
    @ParameterizedTest
    @MethodSource(value= "SimpleTradeTestCases")
    void TestWhenCreateOrderTradesDataCorrect(TradeTestCase ttc) throws Exception {
        for (Security sec : ttc.securities){
            securityRepository.save(sec);
        }
        for (User user : ttc.users){
            userRepository.save(user);
        }
        for (OrderDto orderDto : ttc.orders){
            mockMvc.perform(post("/orders/create", 42L)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(orderDto)))
                    .andExpect(status().isOk());
        }
        Order buyOrder = orderRepository.findByOrderType(OrderType.BUY);
        Order sellOrder = orderRepository.findByOrderType(OrderType.SELL);
        Trade trade = tradeRepository.findAll().get(0);

        long expectedBuyOrderId = buyOrder.getId();
        long expectedSellOrderId = sellOrder.getId();
        int expectedPrice = sellOrder.getPrice();
        int expectedQuantity = buyOrder.getQuantity() > sellOrder.getQuantity() ?
                                    buyOrder.getQuantity()-buyOrder.getRealQuantity() : sellOrder.getQuantity()-sellOrder.getRealQuantity();

        assertEquals(trade.getBuyOrderId(),expectedBuyOrderId);
        assertEquals(trade.getSellOrderId(), expectedSellOrderId);
        assertEquals(trade.getPrice(), expectedPrice);
        assertEquals(trade.getQuantity(), expectedQuantity);
    }

    public static TradeTestCase[] MultipleTradeTestCases(){
        TradeTestCase ttc1 = new TradeTestCase();
        ttc1.securities.add(new Security("WSB"));
        ttc1.users.add(new User("Paper","password"));
        ttc1.users.add(new User("Diamond","password"));
        ttc1.orders.add(new OrderDto(1L,1L, OrderType.BUY,101,50));
        ttc1.orders.add(new OrderDto(2L,1L, OrderType.SELL,100,100));
        ttc1.trades.add(new Trade(2L,1L,100,50));

        TradeTestCase ttc2 = new TradeTestCase();
        ttc2.securities.add(new Security("WSB"));
        ttc2.users.add(new User("Paper","password"));
        ttc2.users.add(new User("Diamond","password"));
        ttc2.users.add(new User("Elon Musk","password"));
        ttc2.orders.add(new OrderDto(1L,1L, OrderType.SELL,100,100));
        ttc2.orders.add(new OrderDto(2L,1L, OrderType.BUY,101,50));
        ttc2.orders.add(new OrderDto(3L,1L, OrderType.BUY,101,50));
        ttc2.trades.add(new Trade(1L,2L,100,50));
        ttc2.trades.add(new Trade(1L,3L,100,50));

        TradeTestCase ttc3 = new TradeTestCase();
        ttc3.securities.add(new Security("WSB"));
        ttc3.users.add(new User("Paper","password"));
        ttc3.users.add(new User("Diamond","password"));
        ttc3.users.add(new User("Elon Musk","password"));
        ttc3.orders.add(new OrderDto(1L,1L, OrderType.BUY,100,80));
        ttc3.orders.add(new OrderDto(2L,1L, OrderType.SELL,88,50));
        ttc3.orders.add(new OrderDto(3L,1L, OrderType.SELL,96,100));
        ttc3.trades.add(new Trade(2L,1L,88,50));
        ttc3.trades.add(new Trade(3L,1L,96,50));

        TradeTestCase ttc4 = new TradeTestCase();
        ttc4.securities.add(new Security("WSB"));
        ttc4.users.add(new User("Paper","password"));
        ttc4.users.add(new User("Diamond","password"));
        ttc4.orders.add(new OrderDto(1L,1L, OrderType.BUY,101,50));
        ttc4.orders.add(new OrderDto(2L,1L, OrderType.BUY,100,100));

        return new TradeTestCase[]{ttc1, ttc2, ttc3, ttc4};
    }
    @ParameterizedTest
    @MethodSource(value= "MultipleTradeTestCases")
    void TestWhenCreateOrderTradesAmountCorrect(TradeTestCase ttc) throws Exception {
        for (Security sec : ttc.securities){
            securityRepository.save(sec);
        }
        for (User user : ttc.users){
            userRepository.save(user);
        }
        for (OrderDto orderDto : ttc.orders){
            mockMvc.perform(post("/orders/create", 42L)
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(orderDto)))
                    .andExpect(status().isOk());
        }
        int expectedCount = ttc.trades.size();
        int count = tradeRepository.findAll().size();
        assertEquals(count,expectedCount);
    }
}

class TradeTestCase{
    public List<OrderDto> orders;
    public List<Trade> trades;
    public List<User> users;
    public List<Security> securities;

    public TradeTestCase() {
        this.orders = new ArrayList<OrderDto>();
        this.trades = new ArrayList<Trade>();
        this.users = new ArrayList<User>();
        this.securities = new ArrayList<Security>();
    }
}