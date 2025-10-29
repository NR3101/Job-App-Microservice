package com.learn.jobms.clients;

import com.learn.jobms.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "REVIEW-SERVICE")
public interface ReviewClient {
    @GetMapping("/api/v1/reviews")
    List<Review> getReviews(@RequestParam("companyId") Long companyId);

    @GetMapping("/api/v1/reviews/bulk")
    List<Review> getReviewsByCompanyIds(@RequestParam("companyIds") List<Long> companyIds);
}
