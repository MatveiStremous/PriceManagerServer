package com.example.pricemanager.service;

import com.example.pricemanager.entity.Company;
import com.example.pricemanager.message.Status;
import com.example.pricemanager.repo.CompanyRepository;

import java.util.List;

public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService() {
        companyRepository = new CompanyRepository();
    }

    public Status createNewCompany(Company company) {
        if (companyRepository.addNewCompany(company)) {
            return Status.SUCCESS;
        } else {
            return Status.COMPANY_ALREADY_EXISTS;
        }
    }

    public List<Company> getAllUserCompanies(int userId) {
        return companyRepository.getCompaniesByUserId(userId);
    }

    public void deleteCompanyById(int companyId) {
        companyRepository.deleteCompanyById(companyId);
    }

    public Status updateCompany(Company company) {
        if (companyRepository.updateCompany(company)) {
            return Status.SUCCESS;
        } else {
            return Status.COMPANY_ALREADY_EXISTS;
        }
    }
}
