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


public class OrderProduct {

    private String id;
    private String order_id;
    private String product_id;
    private int quantity; 
    
}
