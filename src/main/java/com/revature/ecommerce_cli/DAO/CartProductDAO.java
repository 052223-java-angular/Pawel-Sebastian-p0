package com.revature.ecommerce_cli.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import com.revature.ecommerce_cli.models.CartProduct;
import com.revature.ecommerce_cli.util.ConnectionFactory;

// This is the chef
public class CartProductDAO implements CrudDAO<CartProduct> {

    @Override
    public void save(CartProduct obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "INSERT INTO cart_products (id, user_id, product_id, quantity) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, obj.getId());
                ps.setString(2, obj.getUserId());
                ps.setString(3, obj.getProductId());
                ps.setInt(4, obj.getQuantity());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to db");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find db.properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load jdbc");
        }
    }

    @Override
    public void update(CartProduct updater) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE cart_products set user_id = ?, product_id = ?, quantity = ? where id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, updater.getUserId());
                ps.setString(2, updater.getProductId());
                ps.setInt(3, updater.getQuantity());
                ps.setString(4, updater.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to db");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find db.properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load jdbc");
        }
    }

    @Override
    public void delete(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM cart_products WHERE id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to db");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find db.properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load jdbc");
        }
    }

    @Override
    public Optional<CartProduct> findById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM cart_products WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        CartProduct cartProduct = new CartProduct();
                        cartProduct.setId(rs.getString("id"));
                        cartProduct.setUserId(rs.getString("user_id"));
                        cartProduct.setProductId(rs.getString("product_id"));
                        cartProduct.setQuantity(rs.getInt("quantity"));
                        return Optional.of(cartProduct);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to db");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find db.properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load jdbc");
        }

        return Optional.empty();
    }

    @Override
    public List<CartProduct> findAll() {
        List<CartProduct> retArray = new ArrayList<CartProduct>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            Statement s = conn.createStatement();
            try(ResultSet rs = s.executeQuery("select * from cart_products")) {
                while(rs.next()) {
                    // should anyone know the password?
                    CartProduct retCartProduct = new CartProduct(rs.getString("id"), rs.getString("user_id"),
                        rs.getString("product_id"), rs.getInt("quantity"));
                    retArray.add(retCartProduct);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to db");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find db.properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load jdbc");
        }
        return retArray;
    }

    public List<CartProduct> getByUserId(String userId) {
        List<CartProduct> retArray = new ArrayList<CartProduct>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM cart_products WHERE user_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        CartProduct retCartProduct = new CartProduct(rs.getString("id"), rs.getString("user_id"),
                            rs.getString("product_id"), rs.getInt("quantity"));
                        retArray.add(retCartProduct);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to db");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find db.properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load jdbc");
        }

        return retArray;
    }

    public void deleteByUserId(String userId) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM cart_products WHERE user_id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to db");
        } catch (IOException e) {
            throw new RuntimeException("Cannot find db.properties");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unable to load jdbc");
        }
    }
}
