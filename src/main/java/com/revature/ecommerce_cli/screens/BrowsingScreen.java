package com.revature.ecommerce_cli.screens;

import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Session;

import java.util.List;
import java.util.Scanner;
import com.revature.ecommerce_cli.services.RouterService;
import org.apache.logging.log4j.LogManager;
import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.services.ProductService;
import org.apache.logging.log4j.Logger;
import lombok.AllArgsConstructor;

//@AllArgsConstructor
public class BrowsingScreen implements IScreen{
    private final ProductService productService;
    private Session session;
    RouterService router;
    
    
    public BrowsingScreen(ProductService productService, RouterService routerService, Session session) {
        this.productService = productService;
        this.router = routerService;
        this.session = session;
    }



    private static final Logger logger = LogManager.getLogger(BrowsingScreen.class);
    @Override
        public void start(Scanner scan){
        String input = "";
        logger.info("Navigated to Browsing Screen");
        exit: {
            while (true) {
                List<Product> productList = productService.getAll();
                clearScreen();
                System.out.println("Welcome to the product screen, " + session.getUsername() + " !");
                System.out.println("Please select an item below: \n");

                // loop over productList and print each product
                for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                // Assuming the Product class has a toString() method to print details of the product
                System.out.printf("[%2d] View : %-20s -Current Stock: %d\n", (i + 1), product.getName(),
                        product.getInStock());
                }
                
                System.out.println("\n[x] Exit");
                System.out.print("\nEnter: ");
                input = scan.nextLine();
                
                try {
                    int productIndex = Integer.parseInt(input) -1; 
                    if(productIndex >= 0 && productIndex < productList.size()){
                        Product product = productList.get(productIndex);
                        System.out.println("You selected: " + product.getName());
                        router.navigate("/product", scan, productList.get(productIndex));
                        return;
                    } else throw new NumberFormatException();
                } catch(NumberFormatException e) {
                    switch (input) {
                        case "x":
                            logger.info("User exiting out");
                            break exit;
                        default:
                            clearScreen();
                            System.out.println("\nInvalid Option!");
                            System.out.println("\nPress enter to continue...");
                            logger.trace("Browsing screen invalid option");
                            scan.nextLine();
                            break;
                    }
                }
            }
        }
    }

   

    private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    }
 
}
