package com.revature.ecommerce_cli.services;

public class UserService {

    public boolean isValidUsername(String username){

        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    
    
    }


    
}
