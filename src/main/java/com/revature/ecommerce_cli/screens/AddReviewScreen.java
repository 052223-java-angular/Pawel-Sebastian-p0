package com.revature.ecommerce_cli.screens;

import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.services.ReviewService;
import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.services.OrderHistoryService;

public class AddReviewScreen {
    
    private Product product;
    private ReviewService reviewService;
    private final RouterService router;
    private final Session session;


    @Override
    public void start(Scanner scan) {

    String input = "";

    while(true){
        clearScreen();

        

        System.out.println("Welcome to the page for adding a for " + product.getName() + "!");
        System.out.println("Please enter your review below: ");


        
    




}


public boolean verifyWriteAccess() {
    if (product.getOwner().getId() == session.getId()) {
        return true;
    } else {
        return false;
    }
}