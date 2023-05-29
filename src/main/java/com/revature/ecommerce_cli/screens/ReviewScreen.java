package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

import com.revature.ecommerce_cli.models.Review;

import lombok.AllArgsConstructor;
import java.util.List;
import com.revature.ecommerce_cli.services.ReviewService;


@AllArgsConstructor

public class ReviewScreen implements IScreen{
    
    private Review review;
    private Product product;

   
    @Override
    public void start(Scanner scan) {
    
    String input = "";
    while(true){
        clearScreen();
        System.out.println("Welcome to the Reviews Page for ");
        
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


//---------

public void showReviews() {
    Object reviewService;
    List<Review> reviews = reviewService.getAllReviews();
    for (Review review : reviews) {
        System.out.println(review);
    }
}