package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;

import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.models.User;
import lombok.AllArgsConstructor;
import java.util.Optional;
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
                System.out.println("[2] Order History");
                System.out.println("[3]Browse Products");

                System.out.println("\n[x] Exit");
                System.out.print("\nEnter: ");
                input = scan.nextLine();
                switch (input.toLowerCase()) {
                    case "1":
                        logger.info("Navigating to Shopping Cart");
                        router.navigate("/shopping_cart", scan);
                        break;
                    case "2":
                        logger.info("Navigating to Order History");
                        router.navigate("/order_history", scan);
                        break;
                    case "3":
                        logger.info("Navigating to Product Screen");
                        router.navigate("/browsing", scan);
                        break;
                    case "x":
                        logger.info("user sign out");
                        break exit;
                    default:
                        logger.warn("Invalid option!");
                        clearScreen();
                }
            }
        }
    }

   

    private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
        
    


}
    
}
