package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

import com.revature.ecommerce_cli.models.Product;

import lombok.AllArgsConstructor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor

public class ProductScreen implements IScreen{
    
    private Product product;

    private static final Logger logger = LogManager.getLogger(ProductScreen.class);
   
    @Override
    public void start(Scanner scan) {
    
    String input = "";
    while(true){
        logger.debug("Redrawing");
        clearScreen();
        System.out.println("Product: " + product.getName() + "\n Price: " 
        + product.getPrice() + "\nCategory: " 
        + product.getCategory() + "\nIn Stock: " 
        + product.getInStock() + "\nDescription: "
        + product.getDescription() + "\n");
        
        System.out.println("[x] Exit");
        System.out.print("\nEnter: ");
        input = scan.nextLine();


        if(input.equals("x")){
            break;
        }else{
            System.out.println("Invalid input");
        }
    }
    
    }   
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        }
}

