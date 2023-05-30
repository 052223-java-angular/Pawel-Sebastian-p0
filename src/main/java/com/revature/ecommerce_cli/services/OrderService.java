package com.revature.ecommerce_cli.services;


import com.revature.ecommerce_cli.models.CartProduct;
import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.models.OrderProduct;
import com.revature.ecommerce_cli.DAO.OrderDAO;
import com.revature.ecommerce_cli.DAO.OrderProductDAO;
import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DAO.ProductDAO;
import com.revature.ecommerce_cli.DTO.CartItem;

import java.util.List;
import lombok.AllArgsConstructor;
import com.revature.ecommerce_cli.models.CartProduct;
import com.revature.ecommerce_cli.services.CartService;






@AllArgsConstructor
public class OrderService {

    private final OrderDAO orderDAO;
    private final CartProductDAO cartProductDAO;
    private final ProductDAO productDAO;
    private final CartService cartService;
    private final OrderProductDAO orderProductDAO;



    // private OrderService getOrderService(){
    //     return new OrderService(new OrderDAO());
    // }
    

    public void placeOrder(String userId) {
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
        

        Order order = new Order();
        order.setUserId(userId);
        int total = 0;
        for (CartItem cartItem : cartItems) {
            total += cartItem.getQuantity() * cartItem.getUnitPrice();
        }
        order.setAmount(total);
        orderDAO.save(order);

        Order savedOrder = orderDAO.findByUserId(userId).get(0);

        for (CartItem cartItem : cartItems) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrderId(savedOrder.getId());
            orderProduct.setProductId(cartItem.getCartProductId());
            orderProduct.setQuantity(cartItem.getQuantity());
            orderProductDAO.save(orderProduct);

        }
        
        
    }


    
}
