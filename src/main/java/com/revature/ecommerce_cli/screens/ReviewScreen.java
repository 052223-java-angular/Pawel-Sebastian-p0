package com.revature.ecommerce_cli.screens;

import java.util.Scanner;

import com.revature.ecommerce_cli.models.Review;

import lombok.AllArgsConstructor;
import java.util.List;
import com.revature.ecommerce_cli.services.ReviewService;
import com.revature.ecommerce_cli.services.UserService;
import com.revature.ecommerce_cli.models.Product;


@AllArgsConstructor

public class ReviewScreen implements IScreen{
    
    private Product product;
    private ReviewService reviewService;
    private UserService userService;
    
    @Override
    public void start(Scanner scan) {
    
    String input = "";
    while(true){
        clearScreen();
        System.out.println("Welcome to the Review Page for " + product.getName() + "!\n");
        displayReviews();

        System.out.print("\nPress Enter to return: ");
        input = scan.nextLine();
        /*if(input.equals("x")){
            break;
        }else{
            System.out.println("Invalid input");
        }*/
        break;
    }
    
    }   
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        }



//---------

public void displayReviews() {
    List<Review> reviews = reviewService.getReviewsByProductId(product.getId());
    for (Review review : reviews) {
        System.out.println("User: " + userService.getById(review.getUserId()).getUsername());
        System.out.println("Rating: " + review.getRating());
        System.out.println("Comment: \n" + review.getComment() + "\n");
    }
}}