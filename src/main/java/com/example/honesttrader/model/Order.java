package com.example.honesttrader.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="ORDERS")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="USERID")
    private User user;

    @Column(name = "SECURITYID")
    private Long securityId;

    @Column(name = "ORDERTYPE")
    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "QUANTITY")
    private int quantity;

    @Column(name = "REALQUANTITY")
    private int realQuantity;

    @Column(name = "FULLFILLED")
    private boolean fullfilled;

    public Order(User user, Long securityId, OrderType orderType, int price, int quantity) {
        this.user = user;
        this.securityId = securityId;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
        this.realQuantity = quantity;
        this.fullfilled = false;
    }
    public Order(){
    }

    public void applyTrade(Trade trade) {
        this.realQuantity -= trade.getQuantity();
        if (realQuantity == 0) {
            this.fullfilled = true;
        }
    }

}
