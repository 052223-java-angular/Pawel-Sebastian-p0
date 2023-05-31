package com.revature.ecommerce_cli.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class CartProduct {

    private String id;
    private String userId;
    private String productId;
    private int quantity;


    
}
