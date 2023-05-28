package com.revature.ecommerce_cli.screens;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.List;
import java.text.DateFormat;
import java.lang.IndexOutOfBoundsException;
import com.revature.ecommerce_cli.services.OrderHistoryService;
import com.revature.ecommerce_cli.services.RouterService;
import com.revature.ecommerce_cli.services.ProductService;
import com.revature.ecommerce_cli.DTO.OrderItem;
import com.revature.ecommerce_cli.models.Session;
import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.util.PriceUtil;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderHistoryScreen implements IScreen{
    private final OrderHistoryService orderHistoryService;
    private final ProductService productService;
    private Session session;
    RouterService router;
    private static final Logger logger = LogManager.getLogger(OrderHistoryScreen.class);

    @Override
    public void start(Scanner scan) {
        String input = "";
        logger.info("Navigated to Order History Screen");
        List<Order> orders = orderHistoryService.getOrdersByUserId(session.getId());
        logger.info("got orders at Order History Screen");
        DateFormat df = DateFormat.getDateTimeInstance();
        while (true) {
            clearScreen();
            logger.info("redrawing orders");
            System.out.println("----- Order History for " + session.getUsername() + " -----");
            System.out.printf("%-30s %30s %5s\n", "Time Placed", "Amount", "ID");
            int countItem = 1;
            for (Order order : orders) {
                System.out.printf("%-30s %30s %5s\n", df.format(order.getTimePlaced()),
                    PriceUtil.centsToString(order.getAmount()), countItem);
                ++countItem;
            }
            int orderSelect;
            System.out.print("\n Enter Order ID (x to return to Menu): ");
            input = scan.nextLine().toLowerCase();
            if(input.equals("x")) return;
            try {
                orderSelect = Integer.parseInt(input);
                if(orderSelect < 1 || orderSelect > orders.size())
                    throw new IndexOutOfBoundsException("ID out of range");
            } catch (Exception e) {
                System.out.println("invalid ID entered");
                System.out.println(e.getMessage());
                logger.info("invalid ID when selecting order");
                continue;
            }
            if(getOrderItems(orders.get(orderSelect - 1), scan)) return;
        }
   }

    private boolean getOrderItems(Order thisOrder, Scanner scan) {
        String input = "";
        logger.info("Navigated to Order Details Screen");
        List<OrderItem> orderItems = orderHistoryService.getOrderItemsByOrderId(thisOrder.getId());
        while (true) {
            clearScreen();
            logger.info("redrawing order items");
            System.out.printf("%-35s %9s %8s %16s %5s\n", "Product Name", "Unit Price", "Quantity",
                "Total Price", "ID");
            int countItem = 1;
            for (OrderItem item : orderItems) {
                int price = item.getUnitPrice();
                int quantity = item.getQuantity();
                long linePrice = price*(long)quantity;
                System.out.printf("%-35s %9s %8s %16s %5d\n", item.getProductName(),
                    PriceUtil.centsToString(price), quantity, PriceUtil.centsToString(linePrice), countItem);
                ++countItem;
            }
            System.out.println("==============================================================");
            System.out.println("Total: " + PriceUtil.centsToString(thisOrder.getAmount()));
            int productSelect;
            System.out.print("\n Enter Product ID to go to Product, Press Enter to return to Orders: ");
            input = scan.nextLine().toLowerCase();
            if(input.equals("")) return false;
            try {
                productSelect = Integer.parseInt(input);
                if(productSelect < 1 || productSelect > orderItems.size())
                    throw new IndexOutOfBoundsException("ID out of range");
            } catch (Exception e) {
                System.out.println("invalid ID entered");
                System.out.println(e.getMessage());
                logger.info("invalid ID when selecting product");
                System.out.print("\nPress Enter: ");
                scan.nextLine();
                continue;
            }
            logger.info("Navigating to product screen from Order History");
            router.navigate("/product", scan,
                productService.getById(orderItems.get(productSelect - 1).getProductId()));
            return true;
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
