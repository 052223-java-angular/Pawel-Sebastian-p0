package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.services.UserService;
import com.revature.ecommerce_cli.models.User;
import com.revature.ecommerce_cli.models.Session;

import lombok.AllArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
public class RegisterScreen implements IScreen{
    private final UserService userService;
    private final RouterService routerService;
    private Session session;
    private static final Logger logger = LogManager.getLogger(RegisterScreen.class);

    @Override
    public void start(Scanner scan) {
        String input = "";
        String username = "";
        String password = "";

        logger.debug("begin RegisterScreen");
        exit: {

            while(true){
                clearScreen();
                System.out.println("\nWelcome to the registration screen.");
                

                //get username
                username = getUsername(scan);
                if (username.equals("x")){

                    break exit;
                }


                //get password
                password = getPassword(scan);

                if(password.equals("x")){

                    break;

                }

                logger.debug("asking for credential confirmation");
                //confirm users info
                clearScreen();
                System.out.println("Please confirm your credentials.");
                System.out.println("\nUsername :" + username);
                System.out.println("Password: " + password);
                System.out.println("\n Enter (y/n)");

                switch(scan.nextLine()){
                    case "y":
                    User newUser = userService.register(username, password);
                    //session = new Session();
                    session.setSession(newUser);
                    logger.info("created new user " + username);
                    logger.info("routing to menu");
                    routerService.navigate("/menu", scan);
                    break exit;
                    case "n":
                        logger.debug("incorrect credentials");
                        clearScreen();
                        System.out.println("\nRestarting process:");
                        System.out.println("\nPress enter to continue...");
                        scan.nextLine();
                    break;
                    default:
                        logger.debug("invalid y/n choice on confirmation");
                        clearScreen();
                        System.out.println("\nInvalid Option!");
                        System.out.println("\nPress enter to continue...");
                        scan.nextLine();
                    
                        break;
                }

                //break out if info is correct
                break exit; 
            }


        }
    }

/*-------------------------------Helper methods ----------------------------------------*/



public String getUsername(Scanner scan){

    String username = "";

    logger.debug("get username");
    while(true){
    System.out.println("\nEnter Username (x to cancel): ");
    username = scan.nextLine();
    
    
    //scan.nextLine();

    if (username.equalsIgnoreCase("x")){

        return "x";
    }

    if(!userService.isValidUsername(username)){
        logger.info("invalid username " + username);

        clearScreen();
        System.out.println("Username must be 8-20 characters long");
        System.out.println("\nPress enter to continue...");
        scan.nextLine();
        continue;
    }

    if(!userService.isUniqueUsername(username)){
        logger.info("non-unique username");
        clearScreen();
        System.out.println("Sorry that username is taken! Try again: ");
        System.out.println("\nPress enter to continue...");
        scan.nextLine();
        continue;

    }

    break;}
    
    return username;}

    
public String getPassword(Scanner scan){

    String password = "";
    String confirmPassword = "";

    while(true){
        System.out.println("\n Enter a password (x to cancel:)");
        password = scan.nextLine();

        if(password.equalsIgnoreCase("x")){
            return "x";
        }
        if(!userService.isValidPassword(password)){
            clearScreen();
            logger.info("invalid password");
            System.out.println("Password needs to be min 8 characters, and at least 1 letter and 1 number.");
            System.out.println("\nPress enter to continue...");
            scan.nextLine();
            continue;
        }

        System.out.print("\nPlease confirm password (x to cancel):");
        confirmPassword = scan.nextLine();

        if(confirmPassword.equalsIgnoreCase("x")){
            return "x";
        }

        if(!userService.isSamePassword(password, confirmPassword)){
            clearScreen();
            logger.debug("inconsistent passwords");
            System.out.println("Passwords do not match!");
            System.out.println("\nPress enter to continue...");
            scan.nextLine();
            continue;
        }

        break;
    }
    return password;}

private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
        



}
}
