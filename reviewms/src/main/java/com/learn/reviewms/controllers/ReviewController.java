package com.learn.reviewms.controllers;


import com.learn.reviewms.models.Review;
import com.learn.reviewms.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviews(@RequestParam Long companyId) {
        return ResponseEntity.ok(reviewService.getAllReviewsByCompanyId(companyId));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.findByReviewId(reviewId));
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam Long companyId, @Valid @RequestBody Review review) {
        reviewService.createReview(companyId, review);
        return new ResponseEntity<>("Review created", HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody Review reviewDetails) {
        reviewService.updateReview(reviewId, reviewDetails);
        return ResponseEntity.ok("Review updated");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>("Review deleted", HttpStatus.NO_CONTENT);
    }
}
