package com.example.honesttrader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="TRADE")
@Data
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SELLORDERID")
    private Long sellOrderId;

    @Column(name = "BUYORDERID")
    private Long buyOrderId;

    @Column(name = "PRICE")
    private int price;

    @Column(name = "QUANTITY")
    private int quantity;

    public Trade(Long sellOrderId, Long buyOrderId, int price, int quantity) {
        this.sellOrderId = sellOrderId;
        this.buyOrderId = buyOrderId;
        this.price = price;
        this.quantity = quantity;
    }
    public Trade(){}
}
