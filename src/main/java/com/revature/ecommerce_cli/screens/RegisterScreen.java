package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

public class RegisterScreen implements IScreen{

    @Override
    public void start(Scanner scan) {
        String input = "";
        String username = "";
        String password = "";

        exit: {

            while(true){
                clearScreen();
                System.out.println("Welcome to the registration screen.");
             
               

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
    System.out.println("Username (x to cancel): ");
    String username = scan.nextLine();
    
    scan.nextLine();

    if (username.equalsIgnoreCase("x")){

        return "x";
    }
    break;
    }
    return "";
}
    
public String getPassword(Scanner scan){
    return "";}

private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
        



}
}
