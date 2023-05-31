package com.revature.ecommerce_cli.models;

import lombok.AllArgsConstructor;
import java.util.List;



@AllArgsConstructor
public class Cart {

    private String id;
    private String userId;
    private List<CartProduct> cartProducts;
    
}
