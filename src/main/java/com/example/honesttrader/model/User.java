package com.example.honesttrader.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USER")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.orders = new ArrayList<>();
    }
    public User(){
    }
}
