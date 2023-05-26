package com.revature.ecommerce_cli.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.List;
import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.services.CartProductService;
import com.revature.ecommerce_cli.DTO.CartItem;
import com.revature.ecommerce_cli.util.Session;
import com.revature.ecommerce_cli.util.PriceUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShoppingCartScreen implements IScreen{
    private final CartProductService cartProductService;
    private final RouterService router;
    private Session session;
    private static final Logger logger = LogManager.getLogger(ShoppingCartScreen.class);

    @Override
    public void start(Scanner scan) {
        logger.info("Navigated to Shopping Cart Screen");
        List<CartItem> cartItems = cartProductService.getCartItemsByUserId(session.getId);
        int totalPrice = 0;
        exit: {
            while(true) {
                clearScreen();
                System.out.println(session.getUsername() + "'s Shopping Cart");
                System.out.printf("%-40s %9s %8s %10s", "Product Name", "Unit Price", "Quantity", "Total Price");
                for (CartItem item : cartItems) {
                    int price = item.getUnitPrice();
                    int quantity = item.getQuantity();
                    int linePrice = price*quantity;
                    totalPrice += linePrice;
                    System.out.printf("%-40s %9s %8s %10s", item.getProductName(),
                        PriceUtil.toString(price), quantity, PriceUtil.toString(linePrice));
                }
                System.out.println("==============================================================")
                System.out.println("Total: " + PriceUtil.toString(totalPrice));

                System.out.println("\n[1] Modify item quantity");
                System.out.println("[2] Checkout");
                System.out.println("\n[x] Main Menu");

                System.out.println("\nEnter:");

            }
        }
    }

private void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
}
}
