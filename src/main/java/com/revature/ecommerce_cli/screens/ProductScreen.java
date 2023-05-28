package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

import com.revature.ecommerce_cli.models.Product;

import lombok.AllArgsConstructor;

@AllArgsConstructor

public class ProductScreen implements IScreen{
    
    private Product product;

   
    @Override
    public void start(Scanner scan) {
    
    String input = "";
    while(true){
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

