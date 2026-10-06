package service;

import dao.CompanyDAO;
import model.Company;

import java.util.List;

public class CompanyService {

    private CompanyDAO companyDAO;

    public CompanyService() {
        companyDAO = new CompanyDAO();
    }

    // Get all companies
    public List<Company> getAllCompanies() {
        return companyDAO.getAllCompanies();
    }

    // Get company by ID
public Company getCompanyById(int companyId) {
    return companyDAO.getCompanyById(companyId);
}

// Add a new company
public boolean addCompany(Company company) {
    return companyDAO.addCompany(company);
}

// Update company details
public boolean updateCompany(Company company) {
    return companyDAO.updateCompany(company);
}

// Delete company by ID
public boolean deleteCompany(int companyId) {
    return companyDAO.deleteCompany(companyId);
}
}