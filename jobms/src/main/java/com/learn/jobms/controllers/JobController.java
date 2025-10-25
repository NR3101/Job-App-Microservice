package com.learn.jobms.controllers;


import com.learn.jobms.models.Job;
import com.learn.jobms.services.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<Job>> findAll() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<Job> findById(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobService.getJobById(jobId));
    }

    @PostMapping
    public ResponseEntity<String> createJob(@Valid @RequestBody Job job) {
        jobService.createJob(job);
        return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId) {
        jobService.deleteJob(jobId);
        return new ResponseEntity<>("Job deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<String> updateJob(@PathVariable Long jobId, @RequestBody Job jobDetails) {
        jobService.updateJob(jobId, jobDetails);
        return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
    }
}
