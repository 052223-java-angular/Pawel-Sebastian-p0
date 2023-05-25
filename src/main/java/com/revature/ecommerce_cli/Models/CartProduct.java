package com.revature.ecommerce_cli.Models;

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
    private String user_id;
    private String product_id;
    private int quantity;


    
}
