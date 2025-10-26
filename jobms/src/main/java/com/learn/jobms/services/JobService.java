package com.learn.jobms.services;


import com.learn.jobms.dto.JobResponseDTO;
import com.learn.jobms.models.Job;

import java.util.List;

public interface JobService {
    List<JobResponseDTO> getAllJobs();

    void createJob(Job job);

    JobResponseDTO getJobById(Long jobId);

    void deleteJob(Long jobId);

    void updateJob(Long jobId, Job jobDetails);
}

