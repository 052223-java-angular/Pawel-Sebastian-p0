package com.revature.ecommerce_cli.screens;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import com.revature.ecommerce_cli.DAO.OrderDAO;
import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DAO.OrderProductDAO;
import com.revature.ecommerce_cli.models.Session;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class CheckoutScreen implements IScreen {

private Session session;

    @Override
    public void start(Scanner scan){
        String input = "";

        exit:{
            while(true){
                clearScreen();
                System.out.println("Welcome to the Checkout Page" + session.getUsername() + "!");

                System.out.println("\nPlease enter your credit card number below:");
                System.out.println("\nPress x to go back.");
                input = scan.nextLine();
                if(input.equals("x")){
                    break exit;
                }
                if(!validateLength(input)){
                    System.out.println("Invalid credit card number! Please enter a valid 16 digit number.");
                    continue;
                }
                System.out.println("Please enter your credit card expiration date (MM/yy) below:");
                String expirationDate = scan.nextLine();
                if(!validateExpirationDate(expirationDate)){
                    System.out.println("Invalid expiration date! Please enter a valid date.");
                    continue;
                }
                System.out.println("Please enter your credit card CVV below:");
                String cvv = scan.nextLine();
                if(!validateCVV(cvv)){
                    System.out.println("Invalid CVV! Please enter a valid 3 digit CVV.");
                    continue;
                
                }

                createNewOrder();
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth expirationDate = YearMonth.parse(input, formatter);
            YearMonth nowYearMonth = YearMonth.now();
            return expirationDate.isAfter(nowYearMonth);
        }
        public static boolean validateCVV(String input){
            if(input.length() == 3 && input.matches("[0-9]+")){
                return true;
            }
            return false;
        }
        public void createNewOrder(){



            
        }
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        }
}
