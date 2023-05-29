package com.revature.ecommerce_cli.util.custom_exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException () {
        super("User not Found");
    }
}
