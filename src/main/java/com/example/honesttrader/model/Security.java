package com.example.honesttrader.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="SECURITY")
@Data
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    public Security(String name) {
        this.name = name;
    }
    public Security(){}
}
