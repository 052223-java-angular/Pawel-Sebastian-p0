package com.revature.ecommerce_cli.models;

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

public class PaymentMethods {

    private String id;
    private int number;
    private Date expiration_date;
    private String cvc;
    private String user_id;
    
    
    
}