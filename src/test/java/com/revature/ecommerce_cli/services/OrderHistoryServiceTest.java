package com.revature.ecommerce_cli.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import com.revature.ecommerce_cli.models.OrderProduct;
import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.DAO.OrderProductDAO;
import com.revature.ecommerce_cli.DAO.OrderDAO;
import com.revature.ecommerce_cli.DTO.OrderItem;

public class OrderHistoryServiceTest {
    
    private final List<OrderItem> orderItemList = new ArrayList<OrderItem>();

    @Mock 
    private OrderProductDAO orderProductDAO;
    @Mock 
    private OrderDAO orderDAO;

    private OrderHistoryService orderHistoryService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        orderHistoryService = new OrderHistoryService(orderDAO, orderProductDAO);

        orderItemList.add(new OrderItem("48c5c4dc-5d81-416d-89ae-4456ba88723f", "Willow Tree", 3, 2000, "plants"));
        orderItemList.add(new OrderItem("3a42586c-1f54-4638-94e2-95927bf61956", "brown pants", 2, 4099, "apparel"));
    }
    
    @Test
    public void testDeleteById() {
        doNothing().when(orderProductDAO).delete(any(String.class));

        orderHistoryService.deleteById("e8046b97-be23-4aa7-9010-b0f9eff17b69");

        verify(orderProductDAO, times(1)).delete("e8046b97-be23-4aa7-9010-b0f9eff17b69");
    }

    @Test
    public void getOrdersByUserIdTest() {
        List<Order> orders = new ArrayList<Order>();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        orders.add(new Order("a7d6102c-abe0-4a66-88a5-06ce3032dd76", "de00c7b2-f06d-45d3-ae1e-d443d4cad2db",
            1199, new Date()));
        try {
            orders.add(new Order("47040c35-3c6f-42be-92fd-031461e9852a", "de00c7b2-f06d-45d3-ae1e-d443d4cad2db",
                1199, df.parse("07/10/1996")));
        } catch (Exception e) {
            fail(e.getMessage());
        }
        when(orderDAO.findByUserId("de00c7b2-f06d-45d3-ae1e-d443d4cad2db")).thenReturn(orders);
        List<Order> actual = orderHistoryService.getOrdersByUserId("de00c7b2-f06d-45d3-ae1e-d443d4cad2db");
        assertEquals(orders.get(0).getAmount(), actual.get(0).getAmount());
        assertEquals(orders.get(1).getAmount(), actual.get(1).getAmount());
        verify(orderDAO, times(1)).findByUserId("de00c7b2-f06d-45d3-ae1e-d443d4cad2db");
    }

    @Test
    public void getOrderItemsByOrderIdTest() {
        when(orderProductDAO.findOrderItemsByOrderId("904cf680-fdff-11ed-be56-0242ac120002")).
            thenReturn(orderItemList);
        List<OrderItem> actual = orderHistoryService.getOrderItemsByOrderId("904cf680-fdff-11ed-be56-0242ac120002");
        assertEquals(orderItemList.get(1).getProductName(), actual.get(1).getProductName());
        assertEquals(orderItemList.get(0).getUnitPrice(), actual.get(0).getUnitPrice());

        verify(orderProductDAO, times(1)).findOrderItemsByOrderId("904cf680-fdff-11ed-be56-0242ac120002");
    }

    @Test
    public void getOrderProductsByOrderIdTest() {
        List<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
        orderProducts.add(new OrderProduct("4148137f-2cbf-4e72-9515-6b3eb27161fc", 
            "904cf680-fdff-11ed-be56-0242ac120002", "78a727c1-fbb8-49ea-a63a-3b553f417634", 22));
        orderProducts.add(new OrderProduct("7fdc9962-66cc-49b3-86ec-17af2dfb5237",
            "904cf680-fdff-11ed-be56-0242ac120002", "503653b3-9f7c-47f5-923f-9018bd2d4d5b", 2));

        when(orderProductDAO.findByOrderId("904cf680-fdff-11ed-be56-0242ac120002")).
            thenReturn(orderProducts);
        List<OrderProduct> actual = orderHistoryService.
            getOrderProductsByOrderId("904cf680-fdff-11ed-be56-0242ac120002");
        assertEquals(orderProducts.get(1).getQuantity(), actual.get(1).getQuantity());
        assertEquals(orderProducts.get(0).getProductId(), actual.get(0).getProductId());

        verify(orderProductDAO, times(1)).findByOrderId("904cf680-fdff-11ed-be56-0242ac120002");
    }
} 
