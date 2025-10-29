package com.learn.companyms.services.impl;


import com.learn.common.exceptions.ResourceNotFoundException;
import com.learn.companyms.models.Company;
import com.learn.companyms.repositories.CompanyRepository;
import com.learn.companyms.services.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
    }

    @Override
    public List<Company> getCompaniesByIds(List<Long> ids) {
        return companyRepository.findAllById(ids);
    }

    @Override
    public void updateCompany(Long id, Company company) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));

        existingCompany.setName(company.getName());
        existingCompany.setDescription(company.getDescription());

        companyRepository.save(existingCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
        companyRepository.delete(existingCompany);
    }
}
