package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.services.UserService;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.models.User;
import lombok.AllArgsConstructor;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
public class LoginScreen implements IScreen{
    private final UserService userService;
    private final RouterService routerService;
    private Session session;

    private static final Logger logger = LogManager.getLogger(LoginScreen.class);
    

    @Override
    public void start(Scanner scan) {
        String username = "";
        String password = "";

        exit: {
           while(true){
                logger.info("Start Login Screen");
                clearScreen();
                System.out.println("\nWelcome to the login screen.");
                
                //get username
                username = getUsername(scan);
                if (username.equals("x")) {
                    logger.info("Cancel Username");
                    break exit;
                }

                //get password
                password = getPassword(scan);
                if(password.equals("x")) {
                    logger.info("Cancel Password");
                    break exit;
                }

                Optional<User> user = userService.login(username, password);
                if (user.isEmpty()) {
                    System.out.println("\nInvalid login credentials. Please try again.");
                    System.out.println("\nPress enter to continue...");
                    //System.out.println(" " + username + " " );
                    
                    logger.info("Incorrect login attempt for username " + username);
                    scan.nextLine();
                   
                    continue;
                } 
                    session.setSession(user.get());
                    logger.debug("Navigate to Menu, Login successful");
                    routerService.navigate("/menu", scan);
                    return;
            }
        }
    }

    public String getUsername(Scanner scan){
        String username = "";
        while(true){
            System.out.println("\nEnter Username (x to cancel): ");
            username = scan.nextLine();
            
            
            //scan.nextLine();
        
            if (username.equalsIgnoreCase("x")){
        
                return "x";
            }
        break;}
        return username;
    }
    
    public String getPassword(Scanner scan){
        String password = "";
        while(true){
            System.out.println("\nEnter Password (x to cancel): ");
            password = scan.nextLine();
            
            
            //scan.nextLine();
        
            if (password.equalsIgnoreCase("x")){
        
                return "x";
            }
        break;}
        return password;}
    

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
