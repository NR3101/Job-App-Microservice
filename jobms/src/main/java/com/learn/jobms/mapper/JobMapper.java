package com.learn.jobms.mapper;

import com.learn.jobms.dto.JobResponseDTO;
import com.learn.jobms.external.Company;
import com.learn.jobms.external.Review;
import com.learn.jobms.models.Job;

import java.util.List;

public class JobMapper {

    public static JobResponseDTO mapToJobResponseDTO(Job job, Company company, List<Review> reviews) {
        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setMinSalary(job.getMinSalary());
        dto.setMaxSalary(job.getMaxSalary());
        dto.setLocation(job.getLocation());
        dto.setCompany(company);
        dto.setReviews(reviews);
        return dto;
    }
}
