package com.revature.ecommerce_cli.screens;

import java.util.List;
import java.util.Scanner;

import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.models.OrderProduct;
import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Session;

import lombok.AllArgsConstructor;

import com.revature.ecommerce_cli.services.OrderHistoryService;
import com.revature.ecommerce_cli.services.RouterService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AllArgsConstructor

public class ProductScreen implements IScreen{
    
    private Product product;
    private final RouterService router;
    private final Session session;
    private final OrderHistoryService orderHistoryService;

    private static final Logger logger = LogManager.getLogger(ProductScreen.class);
   
    @Override
    public void start(Scanner scan) {
    
    String input = "";
    //log
    while(true){
        logger.debug("Redrawing");
        clearScreen();
        System.out.println("Product: " + product.getName() + "\n Price: " 
        + product.getPrice() + "\nCategory: " 
        + product.getCategory() + "\nIn Stock: " 
        + product.getInStock() + "\nDescription: "
        + product.getDescription() + "\n");
        
        System.out.println("[1] View Reviews for this Product");
        System.out.println("[2] Add Review for this Product");
        System.out.println("[x] Exit");
        System.out.print("\nEnter: ");
        input = scan.nextLine();


        switch(input){
            case "1":
                clearScreen();
                router.navigate("/review", scan, product);
                break;
            case "2":
                clearScreen();
                //if(hasUserOrderedProduct(product.getId())){
                    router.navigate("/addreview", scan, product);
                    //input = "x";
                //}
                // else{
                //     System.out.println("You must purchase this product before you can review it.\n Press enter to continue");
                //     scan.nextLine();
                // }
                break;
            case "x":
                break;

            default:
                System.out.println("Invalid input");
                break;
        }
        if(input.equals("x")){
            break;
        }else{
            System.out.println("Invalid input");
        }
    }
    
    
    }
    public boolean hasUserOrderedProduct(String productId){
        
        List<Order> orders = orderHistoryService.getOrdersByUserId(session.getId());
        for(Order order : orders){
            List<OrderProduct> orderProducts = orderHistoryService.getOrderProductsByOrderId(order.getId());
            for(OrderProduct orderProduct : orderProducts) {
                if(orderProduct.getProductId().equals(productId)){
                    return true;
            }
        
        }
            
        }
        return false;
    }

      
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        }
}

