package com.revature.ecommerce_cli.services;


import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.models.OrderProduct;
import com.revature.ecommerce_cli.models.CartProduct;
import com.revature.ecommerce_cli.DAO.OrderDAO;
import com.revature.ecommerce_cli.DAO.OrderProductDAO;
import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DTO.CartItem;
import com.revature.ecommerce_cli.DAO.ProductDAO;
import com.revature.ecommerce_cli.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderProductDAO orderProductDAO;
    private final CartProductDAO cartProductDAO;
    private final CartService cartService;
    private final ProductDAO productDAO;

    // private OrderService getOrderService(){
    //     return new OrderService(new OrderDAO());
    // }

    public void placeOrder(String userId) {
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
        List<CartProduct> cartProducts = new ArrayList<CartProduct>(cartItems.size());
        int total = 0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getQuantity() * cartItem.getUnitPrice();
            cartProducts.add(cartProductDAO.findById(cartItem.getCartProductId()).get());
        }
        Order order = new Order(userId, total);
        if(!validateStock(order)){
            throw new RuntimeException("Not enough stock");
        }
        orderDAO.save(order);
        for (int i = 0; i < cartItems.size(); i++) {
            String uniqueOrderProductId = generateUniqueOrderProductId(); 
            OrderProduct orderProduct = new OrderProduct(uniqueOrderProductId, order.getId(),
            cartProducts.get(i).getProductId(), cartItems.get(i).getQuantity());
            
            orderProductDAO.save(orderProduct);
            Product product = productDAO.findById(cartProducts.get(i).getProductId()).get();
            product.setInStock(product.getInStock() - cartItems.get(i).getQuantity());
            productDAO.update(product);     


        }

    }
    public boolean validateStock(Order order){

        List<OrderProduct> orderProducts = orderProductDAO.findByOrderId(order.getId());

        for (OrderProduct orderProduct : orderProducts) {
            String productId = orderProduct.getProductId();
            int quantityOrdered = orderProduct.getQuantity();

            Product product = productDAO.findById(productId).get();

            if (product.getInStock() < quantityOrdered) {
                return false;
            }
            
        }
        return true;
    }

    private String generateUniqueOrderProductId() {
        return UUID.randomUUID().toString();
    }
   

}
