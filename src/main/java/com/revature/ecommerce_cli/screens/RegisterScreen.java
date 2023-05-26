package com.revature.ecommerce_cli.screens;

import java.util.Scanner;
import com.revature.ecommerce_cli.services.UserService;
import com.revature.ecommerce_cli.models.User;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class RegisterScreen implements IScreen{
    private final UserService userService;

    @Override
    public void start(Scanner scan) {
        String input = "";
        String username = "";
        String password = "";

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

                //confirm user's info
                clearScreen();
                System.out.println("Please confirm your credentials.");
                System.out.println("\nUsername :" + username);
                System.out.println("Password: " + password);
                System.out.println("\n Enter (y/n)");

                switch(scan.nextLine()){
                    case "y":
                    User newUser = userService.register(username, password);
                    break;
                    case "n":
                        clearScreen();
                        System.out.println("\nRestarting process:");
                        System.out.println("\nPress enter to continue...");
                        scan.nextLine();
                    break;
                    default:
                        clearScreen();
                        System.out.println("\nInvalid Option!");
                        System.out.println("\nPress enter to continue...");
                        scan.nextLine();
                    
                        break;
                }

                //break out if info is correct
                break exit; //remove later for switch
            }


        }
    }

/*-------------------------------Helper methods ----------------------------------------*/



public String getUsername(Scanner scan){

    String username = "";

    while(true){
    System.out.println("\nEnter Username (x to cancel): ");
    username = scan.nextLine();
    
    
    //scan.nextLine();

    if (username.equalsIgnoreCase("x")){

        return "x";
    }

    if(!userService.isValidUsername(username)){

        clearScreen();
        System.out.println("Username must be 8-20 characters long");
        System.out.println("\nPress enter to continue...");
        scan.nextLine();
        continue;
    }

    if(!userService.isUniqueUsername(username)){
        clearScreen();
        System.out.println("Username must be unique!");
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
