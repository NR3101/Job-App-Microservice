package com.learn.jobms.services.impl;


import com.learn.jobms.clients.CompanyClient;
import com.learn.jobms.clients.ReviewClient;
import com.learn.jobms.dto.JobResponseDTO;
import com.learn.jobms.external.Company;
import com.learn.jobms.external.Review;
import com.learn.jobms.mapper.JobMapper;
import com.learn.jobms.models.Job;
import com.learn.jobms.repositories.JobRepository;
import com.learn.jobms.services.JobService;
import com.learn.common.exceptions.ResourceNotFoundException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final CompanyClient companyClient;
    private final ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    @RateLimiter(name = "jobServiceRateLimiter")
//    @CircuitBreaker(name = "jobServiceCircuitBreaker", fallbackMethod = "getAllJobsFallback")
//    @Retry(name = "jobServiceRetry", fallbackMethod = "getAllJobsFallback")
    public List<JobResponseDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        if (jobs.isEmpty()) {
            return List.of();
        }

        // Extract unique company IDs from all jobs
        List<Long> companyIds = jobs.stream()
                .map(Job::getCompanyId)
                .distinct()
                .toList();

        // Fetch all companies and reviews in bulk (2 calls instead of N*2 calls)
        Map<Long, Company> companyMap;
        Map<Long, List<Review>> reviewMap;

        try {
            List<Company> companies = companyClient.getCompaniesByIds(companyIds);
            companyMap = companies.stream()
                    .collect(Collectors.toMap(Company::getId, company -> company));

            List<Review> allReviews = reviewClient.getReviewsByCompanyIds(companyIds);
            reviewMap = allReviews.stream()
                    .collect(Collectors.groupingBy(Review::getCompanyId));
        } catch (Exception e) {
            // Fallback to basic job info if external services fail
            return jobs.stream()
                    .map(job -> JobMapper.mapToJobResponseDTO(job, null, null))
                    .toList();
        }

        // Map jobs to DTOs using the pre-fetched data
        return jobs.stream()
                .map(job -> {
                    Company company = companyMap.get(job.getCompanyId());
                    List<Review> reviews = reviewMap.getOrDefault(job.getCompanyId(), List.of());
                    return JobMapper.mapToJobResponseDTO(job, company, reviews);
                })
                .toList();
    }

    // Fallback method for getAllJobs
    public List<JobResponseDTO> getAllJobsFallback(Throwable t) {
        List<Job> jobs = jobRepository.findAll();
        return jobs.stream()
                .map(job -> JobMapper.mapToJobResponseDTO(job, null, null))
                .toList();
    }

    // Helper method to convert Job to JobResponseDTO(including Company and Review details)
    private JobResponseDTO convertJobToJobResponseDTO(Job job) {
        Company company = companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

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
