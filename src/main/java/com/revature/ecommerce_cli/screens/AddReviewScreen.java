package com.revature.ecommerce_cli.screens;

import java.util.Scanner;
import java.util.UUID;
import java.util.Optional;

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

    private static final Logger logger = LogManager.getLogger(HomeScreen.class);

    @Override
    public void start(Scanner scan) {
    Review review;
    Optional<Review> optReview = reviewService.getByProductIdUserId(product.getId(), session.getId());
    boolean exists = !optReview.isEmpty();
    String input = "";
    logger.debug("starting review adding screen");
    while(true){
        if(exists) {
            System.out.print("Review already exists for product; overwrite? (y/n): ");
            inputLoop: while (true) {
                input = scan.nextLine();
                switch(input) {
                    case "y":
                        review = optReview.get();
                        break inputLoop;
                    case "n":
                        return;
                    default:
                        System.out.println("Invalid input; enter y or n: ");
                }
            }
        } else {
            review = new Review();
            addProductId(review);
            addUserId(review);
            addReviewID(review);
        } 
        clearScreen();
        System.out.println("Welcome to the product review page for: " + product.getName() + "!");
       
        System.out.println("\nPress Enter to continue (x to go back):");
        input = scan.nextLine();

        if(input.equals("x")){
            return;}
        if(addRating(scan, review)) return;
        addReview(scan, review);
        logger.trace("persisting review to database");
        saveReview(review, exists);
        clearScreen();
        System.out.println("Review added! Thanks for your feedback!");

        System.out.println("\nPress Enter to go back to product page:");
        scan.nextLine();
        break;

    }
}

public void addReview(Scanner scan, Review review) {
    logger.debug("adding review comment");
    while(true){
        System.out.println("Please enter your review below: (x to leave blank) ");
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

public void addProductId(Review review){
    review.setProductId(product.getId());
}

public void addReviewID(Review review){
    String id = UUID.randomUUID().toString();
    review.setId(id);
}

public void addUserId(Review review){
    review.setUserId(session.getId());
}

public boolean addRating(Scanner scan, Review review) {
    logger.debug("adding rating in review");
    clearScreen();
    while(true){
        System.out.println("Please enter your rating (a number between 1 and 5): (x to go back) ");
        String rating = "";
        rating = scan.nextLine();
        if(rating.equals("x")){
            return true;
        }else{
            try{
                int intRating = Integer.parseInt(rating);
                if (intRating < 1 || intRating > 5) {
                    throw new NumberFormatException();
                }else{
                
                    logger.trace("setting review rating");
                    review.setRating(intRating);
                    return false;
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
   
    public void saveReview(Review review, boolean exists){
        logger.debug("saving review");
        if(exists) reviewService.updateReview(review);
        else reviewService.saveReview(review);
    }

private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    }
}
