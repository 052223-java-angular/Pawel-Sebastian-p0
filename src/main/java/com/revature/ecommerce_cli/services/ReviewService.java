package com.revature.ecommerce_cli.services;

import java.util.List;
import java.util.Optional;

import com.revature.ecommerce_cli.DAO.ReviewDAO;
import com.revature.ecommerce_cli.models.Review;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReviewService {
    private final ReviewDAO reviewDAO;

    public List<Review> getAll() {
        return reviewDAO.findAll();
    }
    
    public List<Review> getReviewsByProductId(String productId) {
        return reviewDAO.findByProductId(productId);
    }

    // public Review getById(String id) {
    //     Optional<Review> reviewOpt = reviewDAO.findById(id);
    //     return reviewOpt.orElseThrow(ReviewNotFoundException::new);
    // }
}
