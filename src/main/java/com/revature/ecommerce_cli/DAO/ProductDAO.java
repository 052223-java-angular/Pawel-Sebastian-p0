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

import com.revature.ecommerce_cli.models.Product;
import com.revature.ecommerce_cli.util.ConnectionFactory;

public class ProductDAO implements CrudDAO<Product> {

    @Override
    public void save(Product obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "INSERT INTO products (id, name, category, price, in_stock, description)" + 
                " VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, obj.getId());
                ps.setString(2, obj.getName());
                ps.setString(3, obj.getCategory());
                ps.setInt(4, obj.getPrice());
                ps.setInt(5, obj.getInStock());
                ps.setString(6, obj.getDescription());
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
    public void update(Product updater) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE products set name = ?, category = ?, price = ?, in_stock = ?, description = ?" +
            " where id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, updater.getName());
                ps.setString(2, updater.getCategory());
                ps.setInt(3, updater.getPrice());
                ps.setInt(4, updater.getInStock());
                ps.setString(5, updater.getDescription());
                ps.setString(6, updater.getId());
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
            String sql = "DELETE FROM products WHERE id = ?";
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
    public Optional<Product> findById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM products WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Product product = new Product();
                        product.setId(rs.getString("id"));
                        product.setName(rs.getString("name"));
                        product.setCategory(rs.getString("category"));
                        product.setPrice(rs.getInt("price"));
                        product.setInStock(rs.getInt("in_stock"));
                        product.setDescription(rs.getString("description"));
                        return Optional.of(product);
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
    public List<Product> findAll() {
        List<Product> retArray = new ArrayList<Product>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            Statement s = conn.createStatement();
            try(ResultSet rs = s.executeQuery("select * from products order by name")) {
                while(rs.next()) {
                    retArray.add(getFromResultSet(rs));
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

    public List<Product> findByName(String name) {
        List<Product> retArray = new ArrayList<Product>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from products where name ilike ? order by name";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + name + "%");

                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        retArray.add(getFromResultSet(rs));
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

    public List<Product> findByPriceRange(int first, int second) {
        List<Product> retArray = new ArrayList<Product>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from products where price between ? and ? order by price";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, first);
                ps.setInt(2, second);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        retArray.add(getFromResultSet(rs));
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

    public List<Product> findByCategory(String name) {
        List<Product> retArray = new ArrayList<Product>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from products where category ilike '?' order by category, name";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);

                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        retArray.add(getFromResultSet(rs));
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

    private static final Product getFromResultSet(ResultSet rs) throws SQLException {
        return new Product(rs.getString("id"), rs.getString("name"),
            rs.getString("category"), rs.getInt("price"), rs.getInt("in_stock"),
            rs.getString("description"));
    }

}
