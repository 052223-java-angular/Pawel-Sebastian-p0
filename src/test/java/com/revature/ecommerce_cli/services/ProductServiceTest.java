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

import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DTO.CartItem;

public class CartServiceTest {
    
    @Mock 
    private CartProductDAO cartProductDAO;

    private CartService cartService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cartService = new CartService(cartProductDAO);
    }
    
    @Test
    public void testDeleteById() {
        doNothing().when(cartProductDAO).delete(any(String.class));

        cartService.deleteById("e8046b97-be23-4aa7-9010-b0f9eff17b69");

        verify(cartProductDAO, times(1)).delete(any(String.class));
    }

    @Test
    public void updateQuantityById() {
        doNothing().when(cartProductDAO).updateQuantityById(any(String.class), any(int.class));

        cartService.updateQuantityById("a2b48ff2-a830-4209-ae22-743dce500097", 6);
        verify(cartProductDAO, times(1)).updateQuantityById(any(String.class), any(int.class));
    }

    @Test
    public void getCartItemsByUserIdTest() {
        List<CartItem> cartItemList = new ArrayList<CartItem>();
        cartItemList.add(new CartItem("78c79d26-fdff-11ed-be56-0242ac120002", "Clown Shoes", 3, 2300, 1));
        cartItemList.add(new CartItem("06be3b26-fe00-11ed-be56-0242ac120002", "brown pants", 1, 6400, 99));

        when(cartProductDAO.findCartItemsByUserId("904cf680-fdff-11ed-be56-0242ac120002")).
            thenReturn(cartItemList);
        List<CartItem> actual = cartService.getCartItemsByUserId("904cf680-fdff-11ed-be56-0242ac120002");
        assertEquals(cartItemList.get(1).getInStock(), actual.get(1).getInStock());
        assertEquals(cartItemList.get(0).getUnitPrice(), actual.get(0).getUnitPrice());

        verify(cartProductDAO, times(1)).findCartItemsByUserId("904cf680-fdff-11ed-be56-0242ac120002");
    }
} 
