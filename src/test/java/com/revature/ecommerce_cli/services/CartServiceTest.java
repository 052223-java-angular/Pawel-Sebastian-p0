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
    
    private final List<CartItem> cartItemList = new ArrayList<CartItem>();

    @Mock 
    private CartProductDAO cartProductDAO;

    private CartService cartService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        cartService = new CartService(cartProductDAO);

        cartItemList.add(new CartItem("48c5c4dc-5d81-416d-89ae-4456ba88723f", "Clown Shoes", 3, 3200, 1));
        cartItemList.add(new CartItem("3a42586c-1f54-4638-94e2-95927bf61956", "brown pants", 2, 4099, 59));
    }
    
    @Test
    public void testDeleteById() {
        doNothing().when(cartProductDAO).delete(any(String.class));

        cartService.deleteById("e8046b97-be23-4aa7-9010-b0f9eff17b69");

        verify(cartProductDAO, times(1)).delete("e8046b97-be23-4aa7-9010-b0f9eff17b69");
    }

    @Test
    public void updateQuantityById() {
        doNothing().when(cartProductDAO).updateQuantityById(any(String.class), any(int.class));

        cartService.updateQuantityById("a2b48ff2-a830-4209-ae22-743dce500097", 6);
        verify(cartProductDAO, times(1)).updateQuantityById("a2b48ff2-a830-4209-ae22-743dce500097", 6);
    }

    @Test
    public void getCartItemsByUserIdTest() {
        when(cartProductDAO.findCartItemsByUserId("904cf680-fdff-11ed-be56-0242ac120002")).
            thenReturn(cartItemList);
        List<CartItem> actual = cartService.getCartItemsByUserId("904cf680-fdff-11ed-be56-0242ac120002");
        assertEquals(cartItemList.get(1).getInStock(), actual.get(1).getInStock());
        assertEquals(cartItemList.get(0).getUnitPrice(), actual.get(0).getUnitPrice());

        verify(cartProductDAO, times(1)).findCartItemsByUserId("904cf680-fdff-11ed-be56-0242ac120002");
    }
} 
