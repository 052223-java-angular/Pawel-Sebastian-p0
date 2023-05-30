package com.revature.ecommerce_cli.services;

import com.revature.ecommerce_cli.screens.AddReviewScreen;
import com.revature.ecommerce_cli.screens.BrowsingScreen;
import com.revature.ecommerce_cli.screens.HomeScreen;
import com.revature.ecommerce_cli.screens.MenuScreen;
import com.revature.ecommerce_cli.screens.ProductScreen;
import com.revature.ecommerce_cli.screens.LoginScreen;
import com.revature.ecommerce_cli.screens.OrderHistoryScreen;
import com.revature.ecommerce_cli.screens.RegisterScreen;
import com.revature.ecommerce_cli.screens.ReviewScreen;
import com.revature.ecommerce_cli.screens.SearchingScreen;
import com.revature.ecommerce_cli.screens.ShoppingCartScreen;
import com.revature.ecommerce_cli.models.CartProduct;
import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.screens.CheckoutScreen;
import com.revature.ecommerce_cli.services.OrderService;


import lombok.AllArgsConstructor;

import com.revature.ecommerce_cli.DAO.UserDAO;
import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DAO.OrderDAO;
import com.revature.ecommerce_cli.DAO.OrderProductDAO;
import com.revature.ecommerce_cli.DAO.ProductDAO;
import com.revature.ecommerce_cli.DAO.ReviewDAO;

import java.util.Scanner;
//returns screen 

@AllArgsConstructor
//@NoArgsConstructor
public class RouterService {

    private Session session;

    public void navigate(String path, Scanner scan){


        switch(path){
            case "/home":
                new HomeScreen(this).start(scan);
            break;  
            case "/login":
                new LoginScreen(getUserService(), this, session).start(scan);
                break;
            case "/shopping_cart":
                new ShoppingCartScreen(getCartService(),session, this).start(scan);
                break;
            case "/order_history":
                new OrderHistoryScreen(getOrderHistoryService(), getProductService(), session, this).start(scan);
                
            break;
            case "/menu":
                new MenuScreen(session, this).start(scan);
                
            break;
            case "/register":
                new RegisterScreen(getUserService(), this, session).start(scan);
            break;
            case "/browsing":
                new BrowsingScreen(getProductService(), this, session).start(scan);
            break;
            case "/searching":
                new SearchingScreen(session, this, getProductService()).start(scan);
            default:
                System.out.println("Invalid path");
        break;
        }
    }
    public void navigate(String path, Scanner scan, Product product) {
       
    
    switch(path){
        case "/product": 
        new ProductScreen(product, this, session, new OrderHistoryService(new OrderDAO(),new OrderProductDAO()), new CartProduct(), new CartService(new CartProductDAO())).start(scan);
            break;
        case "/review":
            new ReviewScreen(product, getReviewService(), getUserService()).start(scan);
            break;
        
        case "/addreview":
            new AddReviewScreen(product, getReviewService(), this, session).start(scan);
            break;
        case "/checkout":
            new CheckoutScreen(path, scan, session).start(scan);
            break;
        default:
            System.out.println("Invalid path");
            break;
    }
    }
    
    
    
    
    
    
    


/* ----------------- Helper Methods ------------------------- */ 

private UserService getUserService(){

    return new UserService(new UserDAO());

}

private OrderHistoryService getOrderHistoryService(){

    return new OrderHistoryService(new OrderDAO(), new OrderProductDAO());

}

private CartService getCartService(){

    return new CartService(new CartProductDAO());

}
private ProductService getProductService(){
    return new ProductService(new ProductDAO());
}
private ReviewService getReviewService(){
    return new ReviewService(new ReviewDAO());

}}
