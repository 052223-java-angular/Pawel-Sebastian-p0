package com.revature.ecommerce_cli.screens;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.revature.ecommerce_cli.models.CartProduct;
import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.models.OrderProduct;
import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.services.CartService;

import lombok.AllArgsConstructor;

import com.revature.ecommerce_cli.services.OrderHistoryService;
import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.util.PriceUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor

public class ProductScreen implements IScreen{
    
    private Product product;
    private final RouterService router;
    private final Session session;
    private final OrderHistoryService orderHistoryService;
    private final CartProduct cartProduct;
    private final CartService cartService;

    private static final Logger logger = LogManager.getLogger(ProductScreen.class);
   
    @Override
    public void start(Scanner scan) {
        boolean hasReviewed = false;    
        String input = "";
        while(true){
            logger.debug("Redrawing");
            clearScreen();
            System.out.println("Product: " + product.getName() + "\n Price: " 
            + PriceUtil.centsToString(product.getPrice()) + "\nCategory: " 
            + product.getCategory() + "\nIn Stock: " 
            + product.getInStock() + "\nDescription: "
            + product.getDescription() + "\n");
            
            System.out.println("[1] View Reviews for this Product");
            System.out.println("[2] Add Review for this Product");
            System.out.println("[3] Add to Cart");
            System.out.println("[x] Exit");
            System.out.print("\nEnter: ");
            input = scan.nextLine();

            clearScreen();
            switch(input){
                case "1":
                    router.navigate("/review", scan, product);
                    break;
                case "2":
                    /*
                    if(orderHistoryService.hasUserOrderedProduct(product.getId(), session.getId()))
                    */
                    router.navigate("/addreview", scan, product);
                    /*
                        input = "x";
                    }
                    else{
                         System.out.println("You must purchase this product before you can review it.\n Press enter to continue");j
                         scan.nextLine();
                    }
                    */
                    break;
                case "3":
                    addToCart(session, product, cartProduct, scan);
                    break;
                case "x":
                    return;

                default:
                    System.out.println("Invalid input");
                    System.out.print("Press Enter: ");
                    scan.nextLine();
                    break;
            }
            if(input.equals("x")){
                break;
            }else{
                System.out.println("Invalid input");
            }
        }
    }

    public void addToCart(Session session, Product product, CartProduct cartProduct, Scanner scan){

        while(true){
        System.out.println("Enter quantity to add to cart:");
        int quantity = Integer.parseInt(scan.nextLine()); // ensure to handle exceptions here in case of invalid input

        if(quantity > product.getInStock()){
            System.out.println("Not enough in stock to add that many to cart!");
            System.out.println("\nPress Enter to continue");
            scan.nextLine();
            continue;
        }

        cartProduct.setId(UUID.randomUUID().toString());
        cartProduct.setUserId(session.getId());
        cartProduct.setProductId(product.getId());
        cartProduct.setQuantity(quantity);

        cartService.save(cartProduct);

        System.out.println("(" + quantity + ") " + product.getName() + "(s) added to cart!");
        System.out.println("\nPress Enter to continue");
        scan.nextLine();
        break;
    }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        }
}

