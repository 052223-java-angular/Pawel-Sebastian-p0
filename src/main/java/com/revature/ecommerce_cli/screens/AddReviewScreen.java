package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Review;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.services.ReviewService;
import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.services.OrderHistoryService;

public class AddReviewScreen implements IScreen {
    
    private Product product;
    private ReviewService reviewService;
    private final RouterService router;
    private final Session session;
    private Review review;


    @Override
    public void start(Scanner scan) {

    String input = "";

    while(true){
        clearScreen();

        

        System.out.println("Welcome to the product review page for: " + product.getName() + "!");
        System.out.println("Please enter your review below: (x to go back) ");
        System.out.println("Press Enter to continue:");
        input = scan.nextLine();

        if(input.equals("x")){
            break;

        

        clearScreen();
        System.out.println("Please enter your rating (1-5): (x to go back) ");
        input = scan.nextLine();



        



    }


        
    




}

public void setRating(String rating) {
    this.rating = rating;
}

//TODO - move method to verify that the user has write access to the product to the product page
public boolean verifyWriteAccess() {
    if (product.getOwner().getId() == session.getId()) {
        return true;
    } else {
        return false;
    }
}

private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    }
}