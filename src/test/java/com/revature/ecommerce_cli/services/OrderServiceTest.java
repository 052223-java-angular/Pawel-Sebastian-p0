package com.revature.ecommerce_cli.services;

import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.models.OrderProduct;
import com.revature.ecommerce_cli.DAO.OrderDAO;
import com.revature.ecommerce_cli.DAO.OrderProductDAO;
import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DAO.ProductDAO;
import com.revature.ecommerce_cli.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class OrderServiceTest {
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private OrderProductDAO orderProductDAO;
    @Mock 
    private ProductDAO productDAO;
    @Mock
    private CartProductDAO cartProductDAO;
    @Mock 
    private CartService cartService;

    private OrderService orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        orderService = new OrderService(orderDAO, orderProductDAO, cartProductDAO, cartService,
            productDAO);
    }
    @Test
    public void validateStockTest() {
        
        List<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
        OrderProduct firstOrderProduct = new OrderProduct("ac916ac9-5e17-4506-b11a-c56a74844398",
            "37a2f3dc-2fb5-4f65-bd4c-b6cf86806ecc", "e4f1f8a7-5d9f-4336-9c44-db36748486b6", 5);
        OrderProduct secondOrderProduct = new OrderProduct("d19befd9-7f38-424a-ae2a-4453738ce42e",
            "37a2f3dc-2fb5-4f65-bd4c-b6cf86806ecc", "a5c97fe8-d9d8-44a2-9ec0-1412f6ec3c9b", 8);
        
        orderProducts.add(firstOrderProduct);
        when(orderProductDAO.findByOrderId("37a2f3dc-2fb5-4f65-bd4c-b6cf86806ecc")).thenReturn(orderProducts);
        when(productDAO.findById("e4f1f8a7-5d9f-4336-9c44-db36748486b6")).thenReturn(Optional.of(new Product(
            "e4f1f8a7-5d9f-4336-9c44-db36748486b6", "sandals", "footwear", 2300, 10, "slip them on")));
        when(productDAO.findById("a5c97fe8-d9d8-44a2-9ec0-1412f6ec3c9b")).thenReturn(Optional.of(new Product(
            "a5c97fe8-d9d8-44a2-9ec0-1412f6ec3c9b", "oak", "plants", 1000, 4, "will stay around")));
        Order testOrder = new Order("37a2f3dc-2fb5-4f65-bd4c-b6cf86806ecc",
            "432309ed-5070-49e6-9f06-7f7aa3f5c50c", 3300, new Date());
        boolean truthytest = orderService.validateStock(testOrder);
        assertTrue(truthytest);
        orderProducts.add(secondOrderProduct);
        truthytest = orderService.validateStock(testOrder);
        assertFalse(truthytest);
    }
}
