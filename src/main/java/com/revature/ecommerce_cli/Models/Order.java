package com.revature.ecommerce_cli.Models;


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
    private String paymentMethodId;
    
}