package com.revature.app.models;


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
    private double amount;
    private Date timePlaced;
    private String paymentMethodId;


    
}
