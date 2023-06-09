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

import com.revature.ecommerce_cli.models.OrderProduct;
import com.revature.ecommerce_cli.DTO.OrderItem;
import com.revature.ecommerce_cli.util.ConnectionFactory;

// This is the chef
public class OrderProductDAO implements CrudDAO<OrderProduct> {

    @Override
    public void save(OrderProduct obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "INSERT INTO order_products (id, order_id, product_id, quantity) VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, obj.getId());
                ps.setString(2, obj.getOrderId());
                ps.setString(3, obj.getProductId());
                ps.setInt(4, obj.getQuantity());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void update(OrderProduct updater) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE order_products set order_id = ?, product_id = ?, quantity = ? where id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, updater.getOrderId());
                ps.setString(2, updater.getProductId());
                ps.setInt(3, updater.getQuantity());
                ps.setString(4, updater.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public void delete(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM order_products WHERE id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public Optional<OrderProduct> findById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM order_products WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        OrderProduct orderProduct = new OrderProduct();
                        orderProduct.setId(rs.getString("id"));
                        orderProduct.setOrderId(rs.getString("order_id"));
                        orderProduct.setProductId(rs.getString("product_id"));
                        orderProduct.setQuantity(rs.getInt("quantity"));
                        return Optional.of(orderProduct);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }


        return Optional.empty();
    }

    @Override
    public List<OrderProduct> findAll() {
        List<OrderProduct> retArray = new ArrayList<OrderProduct>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            Statement s = conn.createStatement();
            try(ResultSet rs = s.executeQuery("select * from order_products")) {
                while(rs.next()) {
                    // should anyone know the password?
                    OrderProduct retOrderProduct = new OrderProduct(rs.getString("id"), rs.getString("order_id"),
                        rs.getString("product_id"), rs.getInt("quantity"));
                    retArray.add(retOrderProduct);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }

        return retArray;
    }

    public List<OrderProduct> findByOrderId(String orderId) {
        List<OrderProduct> retArray = new ArrayList<OrderProduct>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
           String sql = "SELECT * FROM order_products WHERE order_id = ?";

           try (PreparedStatement ps = conn.prepareStatement(sql)) {
               ps.setString(1, orderId);

               try (ResultSet rs = ps.executeQuery()) {
                   while (rs.next()) {
                       OrderProduct retOrderProduct = new OrderProduct(rs.getString("id"), rs.getString("order_id"),
                           rs.getString("product_id"), rs.getInt("quantity"));
                       retArray.add(retOrderProduct);
                   }
               }
           }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }


        return retArray;
    }

    public List<OrderProduct> findByProductId(String orderId) {
        List<OrderProduct> retArray = new ArrayList<OrderProduct>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
           String sql = "SELECT * FROM order_products WHERE product_id = ?";

           try (PreparedStatement ps = conn.prepareStatement(sql)) {
               ps.setString(1, orderId);

               try (ResultSet rs = ps.executeQuery()) {
                   while (rs.next()) {
                       OrderProduct retOrderProduct = new OrderProduct(rs.getString("id"), rs.getString("order_id"),
                           rs.getString("product_id"), rs.getInt("quantity"));
                       retArray.add(retOrderProduct);
                   }
               }
           }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }


        return retArray;
    }

    public void deleteByOrderId(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "DELETE FROM order_products WHERE order_id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<OrderItem> findOrderItemsByOrderId(String orderId) {
        List<OrderItem> retArray = new ArrayList<OrderItem>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT products.id as product_id, products.name as product_name, quantity, "
                + " products.price as unit_price, in_stock, category FROM order_products join products on " + 
                "product_id = products.id where order_products.order_id = ? order by products.name";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, orderId);

                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        OrderItem retCartItem = new OrderItem(rs.getString("product_id"),
                            rs.getString("product_name"), rs.getInt("quantity"), rs.getInt("unit_price"),
                            rs.getString("category"));
                        retArray.add(retCartItem);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.out.println("couldn't open db.properties");
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("couldn't find postgres driver for jdbc");
            throw new RuntimeException(e.getMessage());
        }
        return retArray;
    }


}
