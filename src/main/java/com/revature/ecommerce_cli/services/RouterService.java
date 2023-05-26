package com.revature.ecommerce_cli.services;

import com.revature.ecommerce_cli.models.User;
import com.revature.ecommerce_cli.screens.*;

import com.revature.ecommerce_cli.DAO.UserDAO;
import java.util.Scanner;
//returns screen 


public class RouterService {

    public void navigate(String path, Scanner scan){


        switch(path){
            case "/home":
                new HomeScreen(this).start(scan);
            break;  
            case "/login":
                new LoginScreen(getUserService()).start(scan);
            break;
            case "/register":
                new RegisterScreen(getUserService()).start(scan);
            break;
        }
    }


/* ----------------- Helper Method ------------------------- */ 

private UserService getUserService(){

    return new UserService(new UserDAO());




}






}
