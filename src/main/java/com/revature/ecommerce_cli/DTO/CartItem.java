package com.revature.ecommerce_cli.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CartItem {
    private String productId;
    private String productName;
    private int quantity;
    private int unitPrice;
    private int inStock;
}
