package com.learn.reviewms.services.impl;


import com.learn.common.exceptions.ResourceNotFoundException;
import com.learn.reviewms.models.Review;
import com.learn.reviewms.repositories.ReviewRepository;
import com.learn.reviewms.services.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> getAllReviewsByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    public List<Review> getReviewsByCompanyIds(List<Long> companyIds) {
        return reviewRepository.findByCompanyIdIn(companyIds);
    }

    @Override
    public void createReview(Long companyId, Review review) {
        if (companyId != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
        } else {
            throw new ResourceNotFoundException("Company with ID " + companyId + " not found.");
        }
    }

    @Override
    public Review findByReviewId(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review with ID " + reviewId + " not found."));
    }

    @Override
    public void updateReview(Long reviewId, Review reviewDetails) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review with ID " + reviewId + " not found."));

        existingReview.setTitle(reviewDetails.getTitle());
        existingReview.setDescription(reviewDetails.getDescription());
        existingReview.setRating(reviewDetails.getRating());
        existingReview.setCompanyId(reviewDetails.getCompanyId());

        reviewRepository.save(existingReview);
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review with ID " + reviewId + " not found."));
        reviewRepository.delete(existingReview);
    }

}
