package com.learn.reviewms.services;



import com.learn.reviewms.models.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviewsByCompanyId(Long companyId);

    List<Review> getReviewsByCompanyIds(List<Long> companyIds);

    void createReview(Long companyId, Review review);

    Review findByReviewId(Long reviewId);

    void updateReview(Long reviewId, Review reviewDetails);

    void deleteReview(Long reviewId);
}
