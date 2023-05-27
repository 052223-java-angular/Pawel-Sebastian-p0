package com.revature.ecommerce_cli.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OrderItem {
    private String orderProductId;
    private String productName;
    private int quantity;
    private int unitPrice;
}
