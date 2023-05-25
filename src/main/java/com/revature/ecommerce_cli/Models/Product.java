package com.revature.ecommerce_cli.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//import java.util.Currency;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class Product {

    private String id;
    private String name;
    private String category;
    private int price;
    
}