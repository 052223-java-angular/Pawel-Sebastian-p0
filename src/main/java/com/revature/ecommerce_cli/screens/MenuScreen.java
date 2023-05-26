package com.revature.ecommerce_cli.screens;

import java.util.Scanner;
import com.revature.ecommerce_cli.services.UserService;
import com.revature.ecommerce_cli.models.User;
import lombok.AllArgsConstructor;
import java.util.Optional;
import com.revature.ecommerce_cli.models.Session;

@AllArgsConstructor
public class MenuScreen implements IScreen {
    private Session session;


    @Override
    public void start(Scanner scan){
        clearScreen();
        System.out.println("Welcome to the menu screen" + session.getUsername() + " !");
        scan.nextLine();
        
    }

   

    private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
        
    


}
    
}
