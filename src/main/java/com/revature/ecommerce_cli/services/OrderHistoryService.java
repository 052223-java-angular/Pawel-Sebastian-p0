package com.revature.ecommerce_cli.services;

import java.util.List;
import com.revature.ecommerce_cli.DAO.OrderProductDAO;
import com.revature.ecommerce_cli.DAO.OrderDAO;
import com.revature.ecommerce_cli.DTO.OrderItem;
import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.models.OrderProduct;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderHistoryService {
    private final OrderDAO orderDAO;
    private final OrderProductDAO orderProductDAO;

    public void deleteById(String id) {
        orderProductDAO.delete(id);
    }

    public List<Order> getOrdersByUserId(String userId) {
        return orderDAO.findByUserId(userId);
    }

    public List<OrderItem> getOrderItemsByOrderId(String orderId) {
        return orderProductDAO.findOrderItemsByOrderId(orderId);
    }
    public List<OrderProduct> getOrderProductsByOrderId(String orderId) {
        return orderProductDAO.findByOrderId(orderId);
    }
}
