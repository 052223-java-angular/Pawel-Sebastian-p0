package com.revature.ecommerce_cli.services;

import java.util.List;
import java.util.Optional;

import com.revature.ecommerce_cli.DAO.CartProductDAO;
import com.revature.ecommerce_cli.DTO.CartItem;
import com.revature.ecommerce_cli.models.CartProduct;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CartService {
    private final CartProductDAO cartProductDAO;

    public void deleteById(String id) {
        cartProductDAO.delete(id);
    }
    public void updateQuantityById(String id, int quantity) {
        cartProductDAO.updateQuantityById(id, quantity);
    }

    public List<CartItem> getCartItemsByUserId(String userId) {
        return cartProductDAO.findCartItemsByUserId(userId);
    }

    public void save(CartProduct cartProduct) {
        cartProductDAO.save(cartProduct);}

    public void clearCart(String userId) {
        cartProductDAO.deleteByUserId(userId);
    }

    public Optional<CartProduct> getByUserAndProductId(String userId, String productId) {
        return cartProductDAO.findByUserAndProductId(userId, productId);
    }
}
