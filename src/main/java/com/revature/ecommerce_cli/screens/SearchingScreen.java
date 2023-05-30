package com.revature.ecommerce_cli.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.services.ProductService;
import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.util.PriceUtil;


@AllArgsConstructor
public class SearchingScreen implements IScreen {
    private Session session;
    private final RouterService router;
    private final ProductService productService;
    private static final Logger logger = LogManager.getLogger(SearchingScreen.class);

    @Override
    public void start(Scanner scan){
        String input = "";
        logger.debug("Navigated to Searching Screen");

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
                        logger.debug("Navigated to Name Searching Screen");
                        productSearch(scan, input);
                        break;
                    case "2":
                        clearScreen();
                        logger.debug("Navigated to Name Searching Screen");
                        categorySearch(scan, input);
                        break;
                    case "3":
                        clearScreen();
                        logger.debug("Navigated to Name Searching Screen");
                        priceSearch(scan, input);
                        break;
                

                    case "x":
                        
                        break exit;
                }
            }
        }
    }

//--------------------------------------Helper Methods ---------------------------------------


public void productSearch(Scanner scan, String input){
        while(true){
        System.out.println("-Searching by Product Name ");
        System.out.println("\n-Enter Product Name:\n  ");
        input = scan.nextLine().trim();
        if(input.isEmpty()){
            System.out.println("Product name can not be empty, please enter a valid product name.");
            System.out.println("Enter x to exit:");
            continue;
        }
        List<Product> products = searchProductByName(input);
        if(products.isEmpty()){
            logger.trace("product search results empty");
            System.out.println("No products found with that name, search again: ");
            continue;
        }
        else{
            logger.trace("building product name search results");
            clearScreen();
            do {
                System.out.println("\n-Products found:");
                System.out.println("\n-Enter item number to proceed to product page, x to go back: \n");
                
                System.out.printf("%-5s %-20s %-15s %-10s %-10s %-20s\n",  "Item #" , "Name:", "Category:", "Price", "In Stock", "Description");
                for(int i = 0; i < products.size(); i++){
                    Product product = products.get(i);
                    System.out.printf("%-5d  %-20s %-15s %-10s %-10d %-20s\n", i +1, product.getName(), 
                    product.getCategory(), PriceUtil.centsToString(product.getPrice()), product.getInStock(), 
                    product.getDescription());
                }
                input = scan.nextLine();
                if(input.equals("x")){
                    return;
                }
                logger.debug("Navigated to Product Page");
            } while(!getProductPage(scan, input, products));
        }
        return;
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

public void categorySearch(Scanner scan, String input){

    List<String> categoryList = productService.allCategories();

    while(true){
        clearScreen();
        System.out.println("-Searching by Product Category ");

        logger.trace("listing product categories");

        System.out.println("\n-Product Categories: \n");
        for(int i = 0; i < categoryList.size(); i++){
            System.out.println("[" + (i+1) + "] " + categoryList.get(i));
        }
        System.out.println("\n-Enter Product Category:\n  (x to go back) ");
        input = scan.nextLine().trim();

        if(input.equalsIgnoreCase("x")){
            break;
        }
        else if(input.isEmpty() || !categoryList.contains(input)){
            clearScreen();
            logger.trace("invalid product category");
            System.out.println("Invalid product category, please enter a valid product category.");
            System.out.println("Press Enter to try again.");
            scan.nextLine();
            continue;
        }
        
        List<Product> products = searchProductByCategory(input);
        if(products.isEmpty()){
            logger.trace("invalid product category - no results");
            System.out.println("No products found with that category, search again: ");
            continue;
        }
        else{
            clearScreen();
            do {
                logger.trace("building product list from category results");
                System.out.println("\n-Products found (x to go back): \n ");
                System.out.printf("%-5s %-20s %-15s %-10s %-10s %-20s\n",  "Item  #", "Name:", "Category:",
                    "Price", "In Stock", "Description");
                
                for(int i = 0; i < products.size(); i++){   
                    Product product = products.get(i);
                    System.out.printf("%-5d %-20s %-15s %-10s %-10d %-20s\n", i+1, product.getName(), 
                    product.getCategory(), PriceUtil.centsToString(product.getPrice()), product.getInStock(), 
                    product.getDescription());
                    
                }
                
                    input = scan.nextLine();
                    if(input.equalsIgnoreCase("x")){    
                        return;
                }
                logger.trace("Going to 'get products' page");
            } while(!getProductPage(scan, input, products));
        }
        return;
    }
}
        
    

public boolean getProductPage(Scanner scan, String input, List<Product> products){
        int productNum;
        try {
            productNum = Integer.parseInt(input);
            if (productNum < 1 || productNum > products.size()) throw new NumberFormatException();
        } catch(NumberFormatException e) {
            logger.debug("invalid product input on products results page");
            clearScreen();
            System.out.println("Invalid input. Please enter a valid product number.");
            return false;
        }
        Product selectedProduct = products.get(productNum - 1);
    
        // Navigate to product screen
        logger.debug("navigating to product page for ", selectedProduct.getName());
        router.navigate("/product", scan, selectedProduct);
        return true;
    }

public List<Product> searchProductByCategory(String input){
    List<Product> products = productService.getByCategory(input);
    if(products.isEmpty()){
        logger.debug("search Product by category " + input + "yielded no results");
        System.out.println("No products found with that category, ");
        return Collections.emptyList();
    }
    return products;
}

public void priceSearch(Scanner scan, String input){
    logger.debug("navigated to price search");
    clearScreen();
    while(true){
        System.out.println("-Searching by Product Price ");
        System.out.print("\n-Enter Product Price Lower Bound ($x.xx):\n  $");
        input = scan.nextLine().trim();
        int lowerBoundCents;
        try{
            lowerBoundCents = PriceUtil.toCents(input);

        }catch(NumberFormatException e){
            System.out.println("Invalid price format (x.xx).");
            logger.debug("invalid price search lower bound");
            continue;
        }

        int upperBoundCents;

        System.out.print("\n-Enter Product Price Upper Bound ($x.xx):\n  $");
        input = scan.nextLine().trim();
        try{
            upperBoundCents = PriceUtil.toCents(input);
        }
        catch(NumberFormatException e){
            System.out.println("Invalid price format (x.xx).");
            logger.debug("invalid price search upper bound");
            continue;
        }

        List<Product> products = productService.getByPrice(lowerBoundCents, upperBoundCents);
        if(products.isEmpty()){
            clearScreen();
            logger.debug("no results for price search");
            System.out.println("No products found with that price, search again: ");
            continue;
        }
        else{
            clearScreen();
            do {
                logger.trace("drawing price search result screen");
                System.out.println("\n-Products found:");
                System.out.println("\n-Enter item number to proceed to product page, x to go back: \n");
                
                System.out.printf("%-5s %-20s %-15s %-10s %-10s %-20s\n",  "Item #" , "Name:", "Category:",
                    "Price", "In Stock", "Description");
                for(int i = 0; i < products.size(); i++){
                    Product product = products.get(i);
                    System.out.printf("%-5d  %-20s %-15s %-10s %-10d %-20s\n", i +1, product.getName(), 
                    product.getCategory(), PriceUtil.centsToString(product.getPrice()), product.getInStock(), 
                    product.getDescription());
                    
                }
                input = scan.nextLine();
                if(input.equals("x")){
                    return;
                }
                logger.debug("navigating to product page");
                
            } while(!getProductPage(scan, input, products));
        }
        return;
    }
}

private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    }
}

