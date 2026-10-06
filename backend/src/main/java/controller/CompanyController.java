package controller;

import model.Company;
import service.CompanyService;

import java.util.List;

public class CompanyController {

    private CompanyService companyService;

    public CompanyController() {
        companyService = new CompanyService();
    }

    // GET ALL COMPANIES
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    // GET COMPANY BY ID
    public Company getCompanyById(int companyId) {
        return companyService.getCompanyById(companyId);
    }

    // ADD COMPANY
    public boolean addCompany(Company company) {
        return companyService.addCompany(company);
    }

    // UPDATE COMPANY
    public boolean updateCompany(Company company) {
        return companyService.updateCompany(company);
    }

    // DELETE COMPANY
    public boolean deleteCompany(int companyId) {
        return companyService.deleteCompany(companyId);
    }
}