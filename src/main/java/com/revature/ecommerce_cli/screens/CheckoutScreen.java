package com.revature.ecommerce_cli.screens;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.services.CartService;
import com.revature.ecommerce_cli.services.OrderService;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class CheckoutScreen implements IScreen {
private Session session;
private OrderService orderService;
private CartService cartService;

    @Override
    public void start(Scanner scan){
        String input = "";

        exit:{
            clearScreen();
            while(true){
                System.out.println("Welcome to the Checkout Page " + session.getUsername() + "!");

                System.out.println("\nPlease enter your credit card number below:");
                System.out.println("\nPress x to go back.");
                input = scan.nextLine();
                while(!validateLength(input)){
                    if(input.equals("x")){
                        break exit;
                    }
                    clearScreen();
                    System.out.println("Invalid credit card number! Please enter a valid 16 digit number.");
                    input = scan.nextLine();
                }
                System.out.println("Please enter your credit card expiration date (MM/yy) below (x: cancel):");
                input = scan.nextLine();
                while (!validateExpirationDate(input)){
                    if(input.equals("x")){
                        break exit;
                    }
                    clearScreen();
                    System.out.println("Invalid expiration date! Please enter a valid date (MM/yy) (x: cancel).");
                    input = scan.nextLine();
                }
                System.out.println("Please enter your credit card CVV below (x to cancel): ");
                input = scan.nextLine();
                while (!validateCVV(input)){
                    if(input.equals("x")){
                        break exit;
                    }
                    clearScreen();
                    System.out.println("Invalid CVV! Please enter a valid 3 digit CVV.");
                    input = scan.nextLine();
                }

                System.out.println("Thank you for your purchase! Your order has been placed.");
                orderService.placeOrder(session.getId());
                cartService.clearCart(session.getId());
                System.out.println("Press Enter to continue.");
                input = scan.nextLine();


                return;
            }
        }
    }

        public static boolean validateLength(String input){
            if(input.length() == 16){
                return true;
            }
            return false;
        }
        public static boolean validateExpirationDate(String input){
            DateTimeFormatter formatter;
            YearMonth expirationDate;
            YearMonth nowYearMonth;
            try {
                formatter = DateTimeFormatter.ofPattern("MM/yy");
                expirationDate = YearMonth.parse(input, formatter);
                nowYearMonth = YearMonth.now();
            } catch (DateTimeParseException e) {
                return false;
            }
            return expirationDate.isAfter(nowYearMonth);
        }
        public static boolean validateCVV(String input){
            if(input.length() == 3 && input.matches("[0-9]+")){
                return true;
            }
            return false;
        }
        
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        }
}