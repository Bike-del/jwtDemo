package com.example.jwtdemo.vo;

import jakarta.persistence.*;
import lombok.Data;

import javax.annotation.processing.Generated;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private long productId;

    @Column(name = "product_name")
    private String productName;


    @Column(name = "product_price")
    private double productPrice;

}
