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
    //private List<Product> productList;
    
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
                System.out.println("Welcome to the browsing screen " + session.getUsername() + " !");
                System.out.println("Please take a glance at the items below:");

                // loop over productList and print each product
                for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                // Assuming the Product class has a toString() method to print details of the product
                System.out.println((i + 1) + ": " + product.toString()); 
            }
                System.out.println("\n[1] Shopping Cart");
                System.out.println("\n[x] Exit");
                System.out.print("\nEnter: ");
                input = scan.nextLine();
                
                switch (input.toLowerCase()) {
                    case "1":
                        logger.info("Navigating to Shopping Cart");
                        router.navigate("/shopping_cart", scan);
                        break;
                    case "2":
                    logger.info("Browse Products");
                    router.navigate("/browseproducts", scan);
                    break;
                    case "3":
                    logger.info("View orders");
                    router.navigate("/vieworders",scan);
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
