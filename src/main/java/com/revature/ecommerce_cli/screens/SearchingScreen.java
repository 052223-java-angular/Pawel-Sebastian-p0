package com.revature.ecommerce_cli.screens;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.services.ProductService;
import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.util.StringUtil;

@AllArgsConstructor
public class SearchingScreen implements IScreen {
    //private static final Logger logger = LogManager.getLogger(SearchingScreen.class);
    private Session session;
    private final RouterService router;
    private final ProductService productService;
    


    @Override
    public void start(Scanner scan){
        String input = "";
        //logger.info("Navigated to Searching Screen");

        exit: {

            while(true){
                clearScreen();
                System.out.println("Welcome to the Searching Screen " + session.getUsername() + " !");

                System.out.println("\n[1] Search by Product Name");
                System.out.println("\n[2] Search by Product Category");
                System.out.println("\n[3] Search by Product Price");

                System.out.println("\n[x] Exit");

                System.out.print("\nEnter: ");
                input = scan.nextLine();

                switch(input.toLowerCase()){
                    case "1":
                        clearScreen();
                        productSearch(scan, input);
                        break;
                    // case "2":
                    // //placeholder
                    //     break;

                    case "x":
                        
                        break exit;




            }



            }
    }
    

        

    }


    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        }


//--------------------------------------Helper Methods ---------------------------------------


public void productSearch(Scanner scan, String input){
    
    
        while(true){
        System.out.println("Searching by Product Name ");
        System.out.println("\nEnter Product Name: ");
        input = scan.nextLine().trim();
        if(input.isEmpty()){
            System.out.println("Product name can not be empty, please enter a valid product name.");
            System.out.println("Enter x to exit:");
            continue;
        }
        List<Product> products = searchProductByName(input);
        if(products.isEmpty()){
            System.out.println("No products found with that name, search again: ");
            continue;
        }
        else{
            System.out.println("Products found: ");
            for(Product product : products){
                System.out.println(product);
                scan.nextLine();
            }
        }
        break;
    }

}


public List<Product> searchProductByName(String input){
    List<Product> products = productService.searchProductByName(input);
    if(products.isEmpty()){
        System.out.println("No products found with that name, ");
        return Collections.emptyList();
    }
    return products;
}
}

