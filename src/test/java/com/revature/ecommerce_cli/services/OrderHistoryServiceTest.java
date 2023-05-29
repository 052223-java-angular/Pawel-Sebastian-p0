package com.revature.ecommerce_cli.services;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

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
    public void getOrderItemsByOrderIdTest() {
        when(orderProductDAO.findOrderItemsByOrderId("904cf680-fdff-11ed-be56-0242ac120002")).
            thenReturn(orderItemList);
        List<OrderItem> actual = orderHistoryService.getOrderItemsByOrderId("904cf680-fdff-11ed-be56-0242ac120002");
        assertEquals(orderItemList.get(1).getProductName(), actual.get(1).getProductName());
        assertEquals(orderItemList.get(0).getUnitPrice(), actual.get(0).getUnitPrice());

        verify(orderProductDAO, times(1)).findOrderItemsByOrderId("904cf680-fdff-11ed-be56-0242ac120002");
    }
} 
