package com.revature.ecommerce_cli.services;

import java.util.List;
import com.revature.ecommerce_cli.DAO.ProductDAO;
import com.revature.ecommerce_cli.models.Product;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;

    
    public List<Product> getAll() {
        return productDAO.findAll();
    }
   

    
}
