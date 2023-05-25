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

public class Review {

    private String id;
    private int rating;
    private String comment;
    private String user_id;
    private String product_id;

}
