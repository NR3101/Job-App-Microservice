package com.learn.jobms.clients;

import com.learn.jobms.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "COMPANY-SERVICE")
public interface CompanyClient {
    @GetMapping("/api/v1/companies/{id}")
    Company getCompany(@PathVariable("id") Long id);

    @GetMapping("/api/v1/companies/bulk")
    List<Company> getCompaniesByIds(@RequestParam("ids") List<Long> ids);
}
