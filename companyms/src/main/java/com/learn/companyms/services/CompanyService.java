package com.learn.companyms.services;

import com.learn.companyms.models.Company;
import jakarta.validation.Valid;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    void createCompany(Company company);

    Company getCompanyById(Long id);

    List<Company> getCompaniesByIds(List<Long> ids);

    void updateCompany(Long id, @Valid Company company);

    void deleteCompany(Long id);
}
