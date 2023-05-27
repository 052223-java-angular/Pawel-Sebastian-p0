package com.revature.ecommerce_cli.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.List;
import com.revature.ecommerce_cli.services.CartProductService;
import com.revature.ecommerce_cli.DTO.CartItem;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.util.PriceUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShoppingCartScreen implements IScreen{
    private final CartProductService cartProductService;
    private Session session;
    private static final Logger logger = LogManager.getLogger(ShoppingCartScreen.class);

    @Override
    public void start(Scanner scan) {
        String input = "";
        logger.info("Navigated to Shopping Cart Screen");
        List<CartItem> cartItems = cartProductService.getCartItemsByUserId(session.getId());
        exit: {
            while(true) {
                clearScreen();
                System.out.println(session.getUsername() + "'s Shopping Cart");
                System.out.printf("%-40s %9s %8s %16s %5s\n", "Product Name", "Unit Price", "Quantity",
                    "Total Price", "ID");
                int totalPrice = 0;
                int countItem = 1;
                for (CartItem item : cartItems) {
                    int price = item.getUnitPrice();
                    int quantity = item.getQuantity();
                    int linePrice = price*quantity;
                    totalPrice += linePrice;
                    System.out.printf("%-40s %9s %8s %16s %5d\n", item.getProductName(),
                        PriceUtil.centsToString(price), quantity, PriceUtil.centsToString(linePrice), countItem);
                    ++countItem;
                }
                System.out.println("==============================================================");
                System.out.println("Total: " + PriceUtil.centsToString(totalPrice));

                System.out.println("\n[1] Modify item quantity");
                System.out.println("[2] Checkout");
                System.out.println("\n[x] Main Menu");

                System.out.print("\nEnter:");
                input = scan.nextLine();
                switch(input.toLowerCase()) {
                    case "1":
                        logger.info("user modifying cart item quantity");
                        modifyQuantity(cartItems);
                        break;
                    case "2":
                        logger.info("user begin checkout");
                        break;
                    case "x":
                        break exit;
                    default:
                        logger.warn("Invalid option!");
                        clearScreen();
                        System.out.println("Invalid option!");
                        System.out.print("\nPress enter to continue");
                        scan.nextLine();
                }
            }
        }
    }
    private void modifyQuantity(List<CartItem> cartItems) {

    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
