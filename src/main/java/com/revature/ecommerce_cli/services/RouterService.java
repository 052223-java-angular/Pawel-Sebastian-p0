package com.revature.ecommerce_cli.services;

import com.revature.ecommerce_cli.models.User;
import com.revature.ecommerce_cli.screens.HomeScreen;
import com.revature.ecommerce_cli.screens.MenuScreen;
import com.revature.ecommerce_cli.screens.LoginScreen;
import com.revature.ecommerce_cli.screens.RegisterScreen;
import com.revature.ecommerce_cli.models.Session;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.revature.ecommerce_cli.DAO.UserDAO;
import java.util.Scanner;
//returns screen 

@AllArgsConstructor
//@NoArgsConstructor
public class RouterService {

    private Session session;

    public void navigate(String path, Scanner scan){


        switch(path){
            case "/home":
                new HomeScreen(this).start(scan);
            break;  
            case "/login":
                new LoginScreen(getUserService(), this, session).start(scan);
                break;
            case "/menu":
                new MenuScreen(session).start(scan);
                
            break;
            case "/register":
                new RegisterScreen(getUserService(), this, session).start(scan);
            break;
        }
    }


/* ----------------- Helper Method ------------------------- */ 

private UserService getUserService(){

    return new UserService(new UserDAO());

}

}
