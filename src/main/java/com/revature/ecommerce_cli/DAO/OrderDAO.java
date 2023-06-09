package com.revature.ecommerce_cli.DAO;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;

import com.revature.ecommerce_cli.models.Order;
import com.revature.ecommerce_cli.util.ConnectionFactory;

// This is the chef
public class OrderDAO implements CrudDAO<Order> {

    private static Date fromTimestamp (Timestamp inTime) {
        return new Date(inTime.getTime());
    }

    @Override

    public void save(Order obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "INSERT INTO orders (id, user_id, amount, time_placed) " +
                "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, obj.getId());
                ps.setString(2, obj.getUserId());
                ps.setInt(3, obj.getAmount());
                ps.setTimestamp(4, new Timestamp(obj.getTimePlaced().getTime()));
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
    public void update(Order updater) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE orders set user_id = ?, amount = ?, time_placed = ?" +
                " where id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, updater.getUserId());
                ps.setInt(2, updater.getAmount());
                ps.setTimestamp(3, new Timestamp(updater.getTimePlaced().getTime()));
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
            String sql = "DELETE FROM orders WHERE id = ?";
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
    public Optional<Order> findById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM orders WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Order order = new Order();
                        order.setId(rs.getString("id"));
                        order.setUserId(rs.getString("user_id"));
                        order.setAmount(rs.getInt("amount"));
                        order.setTimePlaced(fromTimestamp(rs.getTimestamp("time_placed")));
                        return Optional.of(order);
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
    public List<Order> findAll() {
        List<Order> retArray = new ArrayList<Order>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            Statement s = conn.createStatement();
            try(ResultSet rs = s.executeQuery("select * from user")) {
                while(rs.next()) {
                    // should anyone know the password?
                    Order retOrder = new Order(rs.getString("id"), rs.getString("user_id"),
                        rs.getInt("amount"), fromTimestamp(rs.getTimestamp("time_placed")));
                    retArray.add(retOrder);
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

    public List<Order> findByUserId(String userId) {
        List<Order> retArray = new ArrayList<Order>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from orders where user_id = ? order by time_placed";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);

                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Order retOrder = new Order(rs.getString("id"), rs.getString("user_id"),
                            rs.getInt("amount"), fromTimestamp(rs.getTimestamp("time_placed")));
                        retArray.add(retOrder);
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
