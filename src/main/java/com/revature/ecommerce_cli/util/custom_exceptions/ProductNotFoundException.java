package com.revature.ecommerce_cli.util.custom_exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException () {
        super("Product not Found");
    }
}
