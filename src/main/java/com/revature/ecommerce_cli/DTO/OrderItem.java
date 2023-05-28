package com.revature.ecommerce_cli.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderItem {
    private String ProductId;
    private String productName;
    private int quantity;
    private int unitPrice;
}
