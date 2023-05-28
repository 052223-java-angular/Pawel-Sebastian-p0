package com.revature.ecommerce_cli.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.List;
import java.lang.IndexOutOfBoundsException;
import com.revature.ecommerce_cli.services.CartService;
import com.revature.ecommerce_cli.DTO.CartItem;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.util.PriceUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShoppingCartScreen implements IScreen{
    private final CartService cartService;
    private Session session;
    private static final Logger logger = LogManager.getLogger(ShoppingCartScreen.class);

    @Override
    public void start(Scanner scan) {
        String input = "";
        logger.info("Navigated to Shopping Cart Screen");
        List<CartItem> cartItems = cartService.getCartItemsByUserId(session.getId());
        exit: {
            while(true) {
                redrawCart(cartItems);
                System.out.println("\n[1] Modify item quantity");
                System.out.println("[2] Checkout");
                System.out.println("\n[x] Main Menu");

                System.out.print("\nEnter: ");
                input = scan.nextLine();
                switch(input.toLowerCase()) {
                    case "1":
                        logger.info("user modifying cart item quantity");
                        if(cartItems.size() == 0) {
                            logger.info("No Cart items when attempting to modify quantity");
                            System.out.println("Shopping cart empty!");
                            System.out.print("\nPress Enter: ");
                            scan.nextLine();
                            break;
                        }
                        modifyQuantity(cartItems, scan);
                        break;
                    case "2":
                        logger.info("user begin checkout");
                        break exit;
                    case "x":
                        break exit;
                    default:
                        logger.warn("Invalid Shopping cart menu option!");
                        clearScreen();
                        System.out.println("Invalid option!");
                        System.out.print("\nPress enter to continue");
                        scan.nextLine();
                }
            }
        }
    }

    private void redrawCart(List<CartItem> cartItems) {
        clearScreen();
        logger.info("redrawing shopping cart");
        System.out.println(session.getUsername() + "'s Shopping Cart");
        System.out.printf("%-40s %9s %8s %16s %8s %5s\n", "Product Name", "Unit Price", "Quantity",
            "Total Price", "In Stock", "ID");
        int totalPrice = 0;
        int countItem = 1;
        for (CartItem item : cartItems) {
            int price = item.getUnitPrice();
            int quantity = item.getQuantity();
            int linePrice = price*quantity;
            totalPrice += linePrice;
            System.out.printf("%-40s %9s %8s %16s %8s %5d\n", item.getProductName(),
                PriceUtil.centsToString(price), quantity, PriceUtil.centsToString(linePrice), item.getInStock(),
                countItem);
            ++countItem;
        }
        System.out.println("==============================================================");
        System.out.println("Total: " + PriceUtil.centsToString(totalPrice));
    }

    private void modifyQuantity(List<CartItem> cartItems, Scanner scan) {
        String input = "";
        int productSelect;
        clearScreen();
        redrawCart(cartItems);
        while (true) {
            System.out.print("\n Enter ID of product to modify (x to cancel): ");
            input = scan.nextLine().toLowerCase();
            if(input.equals("x")) return;
            try {
                productSelect = Integer.parseInt(input);
                if(productSelect < 1 || productSelect > cartItems.size())
                    throw new IndexOutOfBoundsException("ID out of range");
            } catch (Exception e) {
                System.out.println("invalid ID entered");
                System.out.println(e.getMessage());
                logger.info("invalid ID when modifying item quantity in cart");
                continue;
            }
            break;
        }
        CartItem edited = cartItems.get(productSelect - 1);
        int quantityIn;
        while (true) {
            System.out.print("\n Enter new quantity for " + edited.getProductName() + ": ");
            input = scan.nextLine().toLowerCase();
            if(input.equals("x")) return;
            try {
                quantityIn = Integer.parseInt(input);
                if(quantityIn < 0 || quantityIn > edited.getInStock())
                    throw new IndexOutOfBoundsException("Quantity out of range");
            } catch (Exception e) {
                System.out.println("invalid quantity entered");
                System.out.println(e.getMessage());
                logger.info("invalid quantity when modifying item quantity in cart");
                continue;
            }
            break;
        }
        String cartProductId = edited.getCartProductId();
        if(quantityIn == 0) {

            cartService.deleteById(cartProductId);

            logger.info("deleted CartProduct id + " + cartProductId);
            System.out.println("Deleted " + edited.getProductName() + " from Cart");
            System.out.print("\n press Enter to continue ");
            scan.nextLine();
            return;
        }
        System.out.println("Setting new quantity to " + quantityIn);
        logger.info("changing quantity to " + quantityIn + " for CartProduct id + " + cartProductId);
        cartService.updateQuantityById(edited.getCartProductId(), quantityIn);
        logger.info("updated quantity for for CartProduct id + " + cartProductId);
        edited.setQuantity(quantityIn);
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
