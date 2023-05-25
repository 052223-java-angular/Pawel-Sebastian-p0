package com.revature.ecommerce_cli.services;

import java.util.Optional;
import com.revature.ecommerce_cli.models.User;

import com.revature.ecommerce_cli.DAO.UserDAO;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class UserService {
    private final UserDAO userDao;


    public boolean isValidUsername(String username){

        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    
    
    }

    public boolean isUniqueUsername(String username){
        Optional<User> userOpt = userDao.findByUsername(username);

        if(userOpt.isEmpty()){

            return true;
        }
        return false;
    }
    
}
