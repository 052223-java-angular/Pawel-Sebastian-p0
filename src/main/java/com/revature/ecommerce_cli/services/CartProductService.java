package com.revature.ecommerce_cli.services;

import java.util.List;
import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DTO.CartItem;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartProductService {
    private final CartProductDAO cartProductDAO;

    public List<CartItem> getCartItemsByUserId(String userId) {
        return cartProductDAO.findCartItemsByUserId(userId);
    }
}
