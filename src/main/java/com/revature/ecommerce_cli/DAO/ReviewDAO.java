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

import com.revature.ecommerce_cli.models.Review;
import com.revature.ecommerce_cli.util.ConnectionFactory;

// This is the chef
public class ReviewDAO implements CrudDAO<Review> {
    @Override
    public void save(Review obj) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "INSERT INTO reviews (id, rating, comment, user_id, product_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, obj.getId());
                ps.setInt(2, obj.getRating());
                ps.setString(3, obj.getComment());
                ps.setString(4, obj.getUserId());
                ps.setString(5, obj.getProductId());
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
    public void update(Review updater) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "UPDATE reviews set rating = ?, comment = ?, user_id = ?, product_id = ? where id = ?";
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, updater.getRating());
                ps.setString(2, updater.getComment());
                ps.setString(3, updater.getUserId());
                ps.setString(4, updater.getProductId());
                ps.setString(5, updater.getId());
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
            String sql = "DELETE FROM reviews WHERE id = ?";
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
    public Optional<Review> findById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM reviews WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Review review = new Review();
                        review.setId(rs.getString("id"));
                        review.setRating(rs.getInt("rating"));
                        review.setComment(rs.getString("comment"));
                        review.setUserId(rs.getString("user_id"));
                        review.setProductId(rs.getString("product_id"));
                        return Optional.of(review);
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
    public List<Review> findAll() {
        List<Review> retArray = new ArrayList<Review>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            Statement s = conn.createStatement();
            try(ResultSet rs = s.executeQuery("select * from reviews")) {
                while(rs.next()) {
                    Review retReview = new Review(rs.getString("id"), rs.getInt("rating"),
                        rs.getString("comment"), rs.getString("userId"), rs.getString("productId"));
                    retArray.add(retReview);
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

    public List<Review> findByUserId(String userId) {
        List<Review> retArray = new ArrayList<Review>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM reviews WHERE user_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, userId);

                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Review retReview = new Review(rs.getString("id"), rs.getInt("rating"),
                            rs.getString("comment"), rs.getString("userId"), rs.getString("productId"));
                        retArray.add(retReview);
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

    public List<Review> findByProductId(String productId) {
        List<Review> retArray = new ArrayList<Review>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM reviews WHERE product_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, productId);

                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        Review retReview = new Review(rs.getString("id"), rs.getInt("rating"),
                            rs.getString("comment"), rs.getString("user_id"), rs.getString("product_id"));
                        retArray.add(retReview);
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
    //sql for searching given product name and user name?
    public Optional<Review> findByProductIdUserId(String productId, String userId) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "SELECT * FROM reviews WHERE product_id = ? AND user_id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, productId);
                ps.setString(2, userId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Review review = new Review();
                        review.setId(rs.getString("id"));
                        review.setRating(rs.getInt("rating"));
                        review.setComment(rs.getString("comment"));
                        review.setUserId(rs.getString("user_id"));
                        review.setProductId(rs.getString("product_id"));
                        return Optional.of(review);
                    } else return Optional.empty();
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
    }
}
