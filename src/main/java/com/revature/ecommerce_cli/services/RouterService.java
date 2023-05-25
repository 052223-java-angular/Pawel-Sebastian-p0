package com.revature.ecommerce_cli.services;

import com.revature.ecommerce_cli.screens.HomeScreen;
import com.revature.ecommerce_cli.screens.RegisterScreen;

import java.util.Scanner;
//returns screen 


public class RouterService {

    public void navigate(String path, Scanner scan){


        switch(path){
            case "/home":
                new HomeScreen(this).start(scan);
            break;  
            case "/login":
            break;
            case "/register":
                new RegisterScreen().start(scan);
            break;
        }
    }


    
}
