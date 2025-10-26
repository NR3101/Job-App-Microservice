package com.learn.jobms.dto;

import com.learn.jobms.external.Company;
import com.learn.jobms.models.Job;
import lombok.Data;

@Data
public class JobWithCompanyDTO {
    private Job job;
    private Company company;
}
