package com.revature.ecommerce_cli.services;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

import com.revature.ecommerce_cli.DAO.ReviewDAO;
import com.revature.ecommerce_cli.models.Review;

public class ReviewServiceTest {
    
    @Mock 
    private ReviewDAO reviewDAO;

    private ReviewService reviewService;
    private final List<Review> reviewList = new ArrayList<Review>();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        reviewService = new ReviewService(reviewDAO);
        reviewList.add(new Review("98c38c55-e326-4f88-9d40-174377d42a0f", 5, "Could use more jazz hands",
            "737b4262-e3aa-49e3-bed6-8ffe2c46a081", "7dc7aef7-f031-4bc3-ad94-12a577a54c52"));
        reviewList.add(new Review("8f6bcdbb-e837-46bc-9761-f0be502f4271", 1, "Way too many jazz hands",
            "c9ed2a61-7304-42d6-bc1e-d079be40eb44", "7dc7aef7-f031-4bc3-ad94-12a577a54c52"));
    }

    @Test
    public void getAllTest() {
        when(reviewDAO.findAll()).thenReturn(reviewList);
        List<Review> actual = reviewService.getAll();
        assertEquals(reviewList.get(1).getRating(), actual.get(1).getRating());
        assertEquals(reviewList.get(0).getComment(), actual.get(0).getComment());

        verify(reviewDAO, times(1)).findAll();
    }

    @Test
    public void testGetReviewsByProductId() {
        String productId = "7dc7aef7-f031-4bc3-ad94-12a577a54c52";
        when(reviewDAO.findByProductId(productId)).thenReturn(reviewList);
        List<Review> actual = reviewService.getReviewsByProductId(productId);
        assertEquals(reviewList.get(1).getRating(), actual.get(1).getRating());
        assertEquals(reviewList.get(0).getComment(), actual.get(0).getComment());
        verify(reviewDAO, times(1)).findByProductId(productId);
    }

    @Test
    public void testSave() {
        doNothing().when(reviewDAO).save(any(Review.class));
        reviewService.saveReview(reviewList.get(0));
        verify(reviewDAO, times(1)).save(any(Review.class));

    }
}
