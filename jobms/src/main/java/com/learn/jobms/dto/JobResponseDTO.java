package com.learn.jobms.dto;

import com.learn.jobms.external.Company;
import com.learn.jobms.external.Review;
import lombok.Data;

import java.util.List;

@Data
public class JobResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String minSalary;
    private String maxSalary;
    private String location;
    private Company company;
    private List<Review> reviews;
}
