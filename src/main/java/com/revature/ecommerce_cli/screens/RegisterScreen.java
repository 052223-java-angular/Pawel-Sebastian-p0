package com.revature.ecommerce_cli.screens;

import java.util.Scanner;
import com.revature.ecommerce_cli.services.UserService;

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

                //confirm user's info

                //break out if info is correct
                break exit; //remove later for switch
            }


        }
    }

/*-------------------------------Helper methods ----------------------------------------*/



public String getUsername(Scanner scan){

    while(true){
    System.out.println("\nEnter Username (x to cancel): ");
    String username = scan.nextLine();
    
    
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
    // }
    return "";}

    
public String getPassword(Scanner scan){
    return "";}

private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
        



}
}
