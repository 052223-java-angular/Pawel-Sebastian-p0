package com.revature.ecommerce_cli.screens;

import java.util.Scanner;
import com.revature.ecommerce_cli.services.RouterService;


public class HomeScreen implements IScreen {


private final RouterService router;

public HomeScreen(RouterService router){

    this.router = router;
}


    @Override
    public void start(Scanner scan) {

        String input = "";

        exit: {

            while(true){
                clearScreen();
                System.out.println("Welcome to Ecommerce Store!");
                System.out.println("\n[1] Login screen");
                System.out.println("[2] Register screen");
                System.out.println("[x] Exit");

                System.out.print("\nEnter:");

                input = scan.nextLine();

                switch(input.toLowerCase()){

                    case "1":
                        router.navigate("/login", scan);
                    break;
                    case "2":
                        router.navigate("/register", scan);
                    break;
                    case "x":
                    break exit;

                    default:
                    clearScreen();
                        System.out.println("\nInvalid Option!");
                        System.out.println("\nPress enter to continue...");
                        scan.nextLine();
                        break;
        }
    }
}
            }
            
            /*-------------------------------Helper methods ----------------------------------------*/
        
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        
        
        }
    }
       
    



    

