package dao;

import model.Company;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {

    // Get all companies
    public List<Company> getAllCompanies() {

        List<Company> companies = new ArrayList<>();

        String sql = "SELECT * FROM companies";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Company company = new Company();

                company.setCompanyId(resultSet.getInt("company_id"));
                company.setCompanyName(resultSet.getString("company_name"));
                company.setIndustry(resultSet.getString("industry"));
                company.setLocation(resultSet.getString("location"));
                company.setWebsite(resultSet.getString("website"));
                company.setContactPerson(resultSet.getString("contact_person"));
                company.setContactEmail(resultSet.getString("contact_email"));

                companies.add(company);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return companies;
    }

    // Get company by ID
    public Company getCompanyById(int companyId) {

        String sql = "SELECT * FROM companies WHERE company_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, companyId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Company company = new Company();

                    company.setCompanyId(
                            resultSet.getInt("company_id")
                    );
                    company.setCompanyName(
                            resultSet.getString("company_name")
                    );
                    company.setIndustry(
                            resultSet.getString("industry")
                    );
                    company.setLocation(
                            resultSet.getString("location")
                    );
                    company.setWebsite(
                            resultSet.getString("website")
                    );
                    company.setContactPerson(
                            resultSet.getString("contact_person")
                    );
                    company.setContactEmail(
                            resultSet.getString("contact_email")
                    );

                    return company;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    // Add a new company
public boolean addCompany(Company company) {

    String sql = "INSERT INTO companies " +
                 "(company_name, industry, location, website, " +
                 "contact_person, contact_email) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, company.getCompanyName());
        statement.setString(2, company.getIndustry());
        statement.setString(3, company.getLocation());
        statement.setString(4, company.getWebsite());
        statement.setString(5, company.getContactPerson());
        statement.setString(6, company.getContactEmail());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}
 
// Update company details
public boolean updateCompany(Company company) {

    String sql = "UPDATE companies SET " +
                 "company_name = ?, " +
                 "industry = ?, " +
                 "location = ?, " +
                 "website = ?, " +
                 "contact_person = ?, " +
                 "contact_email = ? " +
                 "WHERE company_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, company.getCompanyName());
        statement.setString(2, company.getIndustry());
        statement.setString(3, company.getLocation());
        statement.setString(4, company.getWebsite());
        statement.setString(5, company.getContactPerson());
        statement.setString(6, company.getContactEmail());
        statement.setInt(7, company.getCompanyId());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

// Delete company by ID
public boolean deleteCompany(int companyId) {

    String sql = "DELETE FROM companies WHERE company_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, companyId);

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}
}