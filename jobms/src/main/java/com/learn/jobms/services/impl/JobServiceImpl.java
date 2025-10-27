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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
    public List<JobResponseDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertJobToJobResponseDTO)
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
