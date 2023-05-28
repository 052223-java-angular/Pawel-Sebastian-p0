package com.revature.ecommerce_cli.services;
import com.revature.ecommerce_cli.util.custom_exceptions.*;

import java.util.List;
import java.util.Optional;

import com.revature.ecommerce_cli.DAO.ProductDAO;
import com.revature.ecommerce_cli.models.Product;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    
    public List<Product> getAll() {
        return productDAO.findAll();
    }


    public List<Product> searchProductByName(String name) {
        return productDAO.findByName(name);
    }
   
    public Product getById(String id) {
        Optional<Product> productOpt = productDAO.findById(id);
        return productOpt.orElseThrow(ProductNotFoundException::new);
    }

    // public List<String> getAllCategories() {
    //     return productDAO.findAllCategories();
    // }
    public List<Product> getByCategory(String category) {
        return productDAO.findByCategory(category);
    }
}
