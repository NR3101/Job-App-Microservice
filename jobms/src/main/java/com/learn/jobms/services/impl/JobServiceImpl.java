package com.learn.jobms.services.impl;


import com.learn.jobms.dto.JobResponseDTO;
import com.learn.jobms.external.Company;
import com.learn.jobms.external.Review;
import com.learn.jobms.mapper.JobMapper;
import com.learn.jobms.models.Job;
import com.learn.jobms.repositories.JobRepository;
import com.learn.jobms.services.JobService;
import com.learn.common.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    RestTemplate restTemplate;

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobResponseDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertJobToJobResponseDTO)
                .toList();
    }

    // Helper method to convert Job to JobResponseDTO(including Company and Review details)
    private JobResponseDTO convertJobToJobResponseDTO(Job job) {
        Company company = restTemplate.getForObject(
                "lb://COMPANY-SERVICE/api/v1/companies/" + job.getCompanyId(),
                Company.class
        );
        ResponseEntity<List<Review>> reviewsResponse = restTemplate.exchange("lb://REVIEW-SERVICE/api/v1/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Review>>() {
                });

        List<Review> reviews = reviewsResponse.getBody();

        return JobMapper.mapToJobResponseDTO(job, company, reviews);
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobResponseDTO getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
        return convertJobToJobResponseDTO(job);
    }

    @Override
    public void deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
        jobRepository.delete(job);
    }

    @Override
    public void updateJob(Long jobId, Job jobDetails) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setMinSalary(jobDetails.getMinSalary());
        job.setMaxSalary(jobDetails.getMaxSalary());
        job.setLocation(jobDetails.getLocation());
        jobRepository.save(job);
    }
}
