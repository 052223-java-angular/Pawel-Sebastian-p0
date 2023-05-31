package com.revature.ecommerce_cli.models;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class Order {
    private String id;
    private String userId;
    private int amount;
    private Date timePlaced;
    
    public Order (String userId, int amount) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.amount = amount;
        this.timePlaced = new Date();
    }
}
