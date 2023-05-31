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
import com.revature.ecommerce_cli.services.RouterService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShoppingCartScreen implements IScreen{
    private final CartService cartService;
    private Session session;
    private final RouterService router;
    private static final Logger logger = LogManager.getLogger(ShoppingCartScreen.class);
    


    @Override
    public void start(Scanner scan) {
        String input = "";
        logger.info("Navigated to " + session.getUsername() + "'s Shopping Cart");
        List<CartItem> cartItems = cartService.getCartItemsByUserId(session.getId());
        exit: {
            while(true) {
                redrawCart(cartItems);
                System.out.println("\n[1] Modify item quantity");
                System.out.println("[2] Remove Item");
                System.out.println("[3] Checkout");
                System.out.println("\n[x] Main Menu");

                System.out.print("\nEnter: ");
                input = scan.nextLine();
                switch(input.toLowerCase()) {
                    case "1":
                        if(cartItems.size() == 0) {
                            logger.info("No Cart items when attempting to modify quantity");
                            System.out.println("Shopping cart empty!");
                            System.out.print("\nPress Enter: ");
                            scan.nextLine();
                            break;
                        }
                        logger.info("user modifying cart item quantity");
                        modifyQuantity(cartItems, scan);
                        break;
                    case "2":
                        if(cartItems.size() == 0) {
                            logger.info("No Cart items when attempting to modify quantity");
                            System.out.println("Shopping cart empty!");
                            System.out.print("\nPress Enter: ");
                            scan.nextLine();
                            break;
                        }
                        logger.info("user removing shopping cart item");
                        removeItem(cartItems, scan);
                        break;
                    case "3":
                        logger.info("user begin checkout");
                        router.navigate("/checkout", scan);
                        break exit;
                    case "x":
                        break exit;
                    default:
                        logger.warn("Invalid Shopping cart menu option!");
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
        System.out.println();
        System.out.printf("%-40s %9s %8s %16s %8s %5s\n", "Product Name", "Unit Price", "Quantity",
            "Total Price", "In Stock", "ID");
        System.out.println();
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

    private void removeItem(List<CartItem> cartItems, Scanner scan) {
        String input = "";
        CartItem removed;
        int productSelect;
        redrawCart(cartItems);
        while (true) {
            System.out.print("\n Enter ID of product to remove (x to cancel): ");
            input = scan.nextLine().toLowerCase();
            if(input.equals("x")) return;
            try {
                productSelect = Integer.parseInt(input);
                if(productSelect < 1 || productSelect > cartItems.size())
                    throw new IndexOutOfBoundsException("ID out of range");
            } catch (Exception e) {
                System.out.println("invalid ID entered");
                System.out.println(e.getMessage());
                logger.info("invalid ID when deleting item in cart");
                continue;
            }
            removed = cartItems.get(productSelect - 1);
            System.out.print(" Enter 'y' to confirm delete " + removed.getProductName() + ": ");
            input = scan.nextLine().toLowerCase();
            if(input.equals("y")) break;
            System.out.println("\n Remove Cancelled");
            logger.info("remove cart item cancelled");
        }
        String cartProductId = removed.getCartProductId();
        cartService.deleteById(cartProductId);
        cartItems.remove(productSelect - 1);
        logger.info("deleted CartProduct id + " + cartProductId);
        System.out.println("Deleted " + removed.getProductName() + " from Cart");
        System.out.print("\n press Enter to continue ");
        scan.nextLine();
        return;
    }

    private void modifyQuantity(List<CartItem> cartItems, Scanner scan) {
        String input = "";
        int inInt;
        boolean shouldRedraw = true;
        outer: while (true) {
            if(shouldRedraw) redrawCart(cartItems);
            shouldRedraw = true;
            System.out.print("\n Enter ID of product to modify (x to cancel): ");
            input = scan.nextLine().toLowerCase();
            if(input.equals("x")) return;
            try {
                inInt = Integer.parseInt(input);
                if(inInt < 1 || inInt > cartItems.size()) throw new IndexOutOfBoundsException("ID out of range");
            } catch (Exception e) {
                System.out.println("invalid ID entered");
                System.out.println(e.getMessage());
                logger.info("invalid ID when modifying item quantity in cart");
                shouldRedraw = false;
                continue outer;
            }
            CartItem edited = cartItems.get(inInt - 1);
            int quantityIn;
            inner: while (true) {
                System.out.print("\n Enter new quantity for " + edited.getProductName() + " (x to cancel): ");
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
                    continue inner;
                }
                break inner;
            }
            String cartProductId = edited.getCartProductId();
            if(quantityIn == 0) {
                System.out.print(" Enter 'y' to confirm remove " + edited.getProductName() + ": ");
                input = scan.nextLine().toLowerCase();
                if(!input.equals("y")) {
                    shouldRedraw = false;
                    redrawCart(cartItems);
                    System.out.println("\n Remove Cancelled");
                    logger.info("set Cart item quantity to 0 cancelled");
                    continue outer;}
                cartService.deleteById(cartProductId);
                cartItems.remove(inInt - 1);
                logger.info("deleted CartProduct id + " + cartProductId);
                System.out.println("Removed " + edited.getProductName() + " from Cart");
                System.out.print("\n press Enter to continue ");
                scan.nextLine();
                return;
            }
            System.out.println("Setting new quantity to " + quantityIn);
            logger.info("changing quantity to " + quantityIn + " for CartProduct id + " + cartProductId);
            cartService.updateQuantityById(edited.getCartProductId(), quantityIn);
            logger.info("updated quantity for for CartProduct id + " + cartProductId);
            edited.setQuantity(quantityIn);
            break;
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
