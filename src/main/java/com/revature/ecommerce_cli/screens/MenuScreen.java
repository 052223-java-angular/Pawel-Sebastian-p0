package com.revature.ecommerce_cli.screens;

import java.util.Scanner;


import com.revature.ecommerce_cli.services.RouterService;
import lombok.AllArgsConstructor;
import com.revature.ecommerce_cli.models.Session;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor
public class MenuScreen implements IScreen {
    private Session session;
    private final RouterService router;
    private static final Logger logger = LogManager.getLogger(MenuScreen.class);

    @Override
    public void start(Scanner scan){
        String input = "";
        logger.info("Navigated to Menu Screen");
        exit: {
            while (true) {
                clearScreen();
                System.out.println("Welcome to the menu screen " + session.getUsername() + " !");
                System.out.println("\n[1] Shopping Cart");
                System.out.println("\n[2] Browse Products");
                System.out.println("\n[3] Search Products");
                System.out.println("\n[4] Order History");
                System.out.println("\n[x] Exit");
                System.out.print("\nEnter: ");
                input = scan.nextLine();
                switch (input.toLowerCase()) {
                    case "1":
                        logger.info("Navigating to Shopping Cart");
                        router.navigate("/shopping_cart", scan);
                        break;
                    case "2":
                        logger.info("Navigating to Product Screen");
                        router.navigate("/browsing", scan);
                        break;
                    case "3":
                        logger.info("Navigating to Searching Screen");
                        router.navigate("/searching", scan);        
                        break;
                    case "4":
                        logger.info("Navigating to Order History");
                        router.navigate("/order_history", scan);
                        break;
                   
                    case "x":
                        logger.info("user sign out");
                        break exit;
                    default:
                        clearScreen();
                        System.out.println("\nInvalid Option!");
                        System.out.println("\nPress enter to continue...");
                        logger.trace("Menu screen invalid option");
                        scan.nextLine();
                        break;
                }
            }
        }
    }

   

    private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
        
    


}
    
}
