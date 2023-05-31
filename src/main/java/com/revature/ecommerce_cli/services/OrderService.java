package com.revature.ecommerce_cli.services;


import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.models.OrderProduct;
import com.revature.ecommerce_cli.models.CartProduct;
import com.revature.ecommerce_cli.DAO.OrderDAO;
import com.revature.ecommerce_cli.DAO.OrderProductDAO;
import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DTO.CartItem;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderService {
    private final OrderDAO orderDAO;
    private final OrderProductDAO orderProductDAO;
    private final CartProductDAO cartProductDAO;
    private final CartService cartService;

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
        orderDAO.save(order);
        for (int i = 0; i < cartItems.size(); i++) {
            OrderProduct orderProduct = new OrderProduct(cartItems.get(i).getCartProductId(), order.getId(),
                cartProducts.get(i).getProductId(), cartItems.get(i).getQuantity());
            orderProductDAO.save(orderProduct);
        }
    }
}
