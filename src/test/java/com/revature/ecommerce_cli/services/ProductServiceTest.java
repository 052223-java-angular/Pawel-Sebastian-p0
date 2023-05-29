package com.revature.ecommerce_cli.services;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import com.revature.ecommerce_cli.DAO.ProductDAO;
import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.util.custom_exceptions.ProductNotFoundException;

public class ProductServiceTest {
    
    private final List<Product> productList = new ArrayList<Product>();

    @Mock 
    private ProductDAO productDAO;

    private ProductService productService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        productService = new ProductService(productDAO);
        productList.add(new Product("78c79d26-fdff-11ed-be56-0242ac120002", "Clown Shoes", "footwear",
           2300, 1, "big and impractical"));
        productList.add(new Product("06be3b26-fe00-11ed-be56-0242ac120002", "brown pants", "apparel",
           6400, 99, "sturdy and reliable"));
    }

    @Test
    public void searchProductByNameTest() {
        List<Product> similarNamesList = new ArrayList<Product>();

        similarNamesList.add(new Product("d2c99a7a-3dbe-4f27-810c-46923d3a768f", "strawberry jam", "food",
            499, 59, "delicious jam"));
        similarNamesList.add(new Product("ac94c923-f846-4392-9802-f5d3a178e85b", "Pure Jam", "song",
            299, 9999, "song by YMO"));
        when(productDAO.findByName("jam")).thenReturn(similarNamesList);
        List<Product> actual = productService.searchProductByName("jam");
        assertEquals(similarNamesList.get(1).getInStock(), actual.get(1).getInStock());
        assertEquals(similarNamesList.get(0).getDescription(), actual.get(0).getDescription());

        verify(productDAO, times(1)).findByName("jam");
    }

    @Test
    public void getByIdTest() {
        String id = "c5c790b7-52b0-4e06-9f5e-36263bcb29f4";
        String nonExistent = "7c977c74-f03c-4f56-ba8b-a1a8769b70af";
        when(productDAO.findById(id)).thenReturn(
            Optional.of(new Product(id, "willow tree", "plants", 900, 25, "weeping")));
        when(productDAO.findById(nonExistent)).thenReturn(Optional.empty());

        assertEquals(900, productService.getById("c5c790b7-52b0-4e06-9f5e-36263bcb29f4").getPrice());
        verify(productDAO, times(1)).findById("c5c790b7-52b0-4e06-9f5e-36263bcb29f4");
    }

    @Test (expected = ProductNotFoundException.class)
    public void getByIdExceptionTest() {
        String id = "c5c790b7-52b0-4e06-9f5e-36263bcb29f4";
        String nonExistent = "7c977c74-f03c-4f56-ba8b-a1a8769b70af";
        when(productDAO.findById(id)).thenReturn(
            Optional.of(new Product(id, "willow tree", "plants", 900, 25, "weeping")));
        when(productDAO.findById(nonExistent)).thenReturn(Optional.empty());

        productService.getById("7c977c74-f03c-4f56-ba8b-a1a8769b70af");
        verify(productDAO, times(1)).findById("7c977c74-f03c-4f56-ba8b-a1a8769b70af");
    }

    @Test
    public void getByCategoryTest() {
        List<Product> categoryList = new ArrayList<Product>();
        categoryList.add(productList.get(1));

        when(productDAO.findByCategory("apparel")).thenReturn(categoryList);

        assertEquals(6400, productService.getByCategory("apparel").get(0).getPrice());
        verify(productDAO, times(1)).findByCategory("apparel");
    }

    @Test
    public void allCategoriesTest() {
        List<String> categoryNames = new ArrayList<String>(List.of("apparel", "footwear"));
        when(productDAO.getAllCategories()).thenReturn(categoryNames);
        List<String> actualNames = productService.allCategories();
        assertEquals(categoryNames.get(0), actualNames.get(0));
        assertEquals(categoryNames.get(1), actualNames.get(1));
        verify(productDAO, times(1)).getAllCategories();
    }

    @Test
    public void getByPriceTest() {
        List<Product> expensive = new ArrayList<Product>();
        expensive.add(productList.get(1));
        when(productDAO.findByPriceRange(3000, 10000)).thenReturn(expensive);
        assertEquals(6400, productService.getByPrice(3000, 10000).get(0).getPrice());
        verify(productDAO, times(1)).findByPriceRange(3000, 10000);
    }

    @Test
    public void getAllTest() {
        when(productDAO.findAll()).thenReturn(productList);
        List<Product> actual = productService.getAll();
        assertEquals(productList.get(1).getInStock(), actual.get(1).getInStock());
        assertEquals(productList.get(0).getDescription(), actual.get(0).getDescription());

        verify(productDAO, times(1)).findAll();
    }
} 
