package com.revature.ecommerce_cli.screens;

import java.util.Scanner;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Review;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.services.ReviewService;
import com.revature.ecommerce_cli.services.RouterService;

import lombok.AllArgsConstructor;

//import com.revature.ecommerce_cli.services.OrderHistoryService;

@AllArgsConstructor
public class AddReviewScreen implements IScreen {
    
    private Product product;
    private ReviewService reviewService;
    private final RouterService router;
    private final Session session;
    private Review review;

    private static final Logger logger = LogManager.getLogger(HomeScreen.class);

    @Override
    public void start(Scanner scan) {

    String input = "";
    logger.debug("starting review adding screen");
    while(true){
        addProductId();
        addUserId();
        addReviewID();
        //addRating(scan);
        clearScreen();
        System.out.println("Welcome to the product review page for: " + product.getName() + "!");
       
        System.out.println("\nPress Enter to continue (x to go back):");
        input = scan.nextLine();

        if(input.equals("x")){
            break;}
        addRating(scan);
        addReview(scan);
        logger.trace("persisting review to database");
        saveReview();
        clearScreen();
        System.out.println("Review added! Thanks for your feedback!");

        System.out.println("\nPress Enter to go back to product page:");
        scan.nextLine();
        break;

    }
}

public void addReview(Scanner scan) {
    logger.debug("adding review comment");
    while(true){
        System.out.println("Please enter your review below: (x to cancel) ");
        String input = scan.nextLine();
        if(input.equals("x")){
            logger.trace("review comment cancelled");
            review.setComment("");
            break;
        }else{
            logger.trace("setting review comment");
            review.setComment(input);
            break;
        }
    }
   
}

public void addProductId(){
    review.setProductId(product.getId());
}

public void addReviewID(){
    String id = UUID.randomUUID().toString();
    review.setId(id);
}

public void addUserId(){
    review.setUserId(session.getId());
}

public void addRating(Scanner scan) {

    logger.debug("adding rating in review");
    clearScreen();
    while(true){
        System.out.println("Please enter your rating (a number between 1 and 5): (x to go back) ");
        String rating = "";
        rating = scan.nextLine();
        if(rating.equals("x")){
            break;
        }else{
            try{
                int intRating = Integer.parseInt(rating);
                if (intRating < 1 || intRating > 5) {
                    throw new NumberFormatException();
                }else{
                
                    logger.trace("setting review rating");
                    review.setRating(intRating);
                    break;
                }
            }
            catch(NumberFormatException e){
                logger.trace("rating out of range");
                System.out.println("Invalid rating, please enter a number between 1 and 5");
                continue;
            }
        }
    }
}
   
    public void saveReview(){
        logger.debug("saving review");
        reviewService.saveReview(review);
    }

private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    }
}
