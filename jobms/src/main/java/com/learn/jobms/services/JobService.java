package com.learn.jobms.services;


import com.learn.jobms.models.Job;

import java.util.List;

public interface JobService {
    List<Job> getAllJobs();

    void createJob(Job job);

    Job getJobById(Long jobId);

    void deleteJob(Long jobId);

    void updateJob(Long jobId, Job jobDetails);
}

