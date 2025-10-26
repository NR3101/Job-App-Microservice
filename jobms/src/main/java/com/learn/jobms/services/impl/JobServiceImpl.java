package com.learn.jobms.services.impl;


import com.learn.jobms.dto.JobWithCompanyDTO;
import com.learn.jobms.external.Company;
import com.learn.jobms.models.Job;
import com.learn.jobms.repositories.JobRepository;
import com.learn.jobms.services.JobService;
import com.learn.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream()
                .map(this::convertJobToJobWithCompanyDTO)
                .toList();
    }

    // Helper method to convert Job to JobWithCompanyDTO
    private JobWithCompanyDTO convertJobToJobWithCompanyDTO(Job job) {
        JobWithCompanyDTO dto = new JobWithCompanyDTO();
        dto.setJob(job);

        RestTemplate restTemplate = new RestTemplate();
        String companyServiceUrl = "http://localhost:8081/api/v1/companies/" + job.getCompanyId();
        try {
            Company company = restTemplate.getForObject(companyServiceUrl, Company.class);
            dto.setCompany(company);
        } catch (Exception e) {
            dto.setCompany(null);
        }

        return dto;
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
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
