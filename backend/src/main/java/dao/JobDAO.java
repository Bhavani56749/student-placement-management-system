package dao;

import model.Job;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    public List<Job> getAllJobs() {

        List<Job> jobs = new ArrayList<>();

        String sql = "SELECT * FROM jobs";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Job job = new Job();

                job.setJobId(resultSet.getInt("job_id"));
                job.setCompanyId(resultSet.getInt("company_id"));
                job.setJobTitle(resultSet.getString("job_title"));
                job.setJobDescription(resultSet.getString("job_description"));
                job.setRequiredSkills(resultSet.getString("required_skills"));
                job.setMinimumCgpa(resultSet.getDouble("minimum_cgpa"));
                job.setEligibleDepartment(resultSet.getString("eligible_department"));
                job.setGraduationYear(resultSet.getInt("graduation_year"));
                job.setSalaryPackage(resultSet.getDouble("salary_package"));
                job.setApplicationDeadline(
                        resultSet.getString("application_deadline")
                );
                job.setJobLocation(resultSet.getString("job_location"));

                jobs.add(job);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jobs;
    }

    // Get job by ID
public Job getJobById(int jobId) {

    String sql = "SELECT * FROM jobs WHERE job_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, jobId);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                Job job = new Job();

                job.setJobId(resultSet.getInt("job_id"));
                job.setCompanyId(resultSet.getInt("company_id"));
                job.setJobTitle(resultSet.getString("job_title"));
                job.setJobDescription(resultSet.getString("job_description"));
                job.setRequiredSkills(resultSet.getString("required_skills"));
                job.setMinimumCgpa(resultSet.getDouble("minimum_cgpa"));
                job.setEligibleDepartment(
                        resultSet.getString("eligible_department")
                );
                job.setGraduationYear(
                        resultSet.getInt("graduation_year")
                );
                job.setSalaryPackage(
                        resultSet.getDouble("salary_package")
                );
                job.setApplicationDeadline(
                        resultSet.getString("application_deadline")
                );
                job.setJobLocation(
                        resultSet.getString("job_location")
                );

                return job;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

// Add a new job
public boolean addJob(Job job) {

    String sql = "INSERT INTO jobs " +
                 "(company_id, job_title, job_description, required_skills, " +
                 "minimum_cgpa, eligible_department, graduation_year, " +
                 "salary_package, application_deadline, job_location) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, job.getCompanyId());
        statement.setString(2, job.getJobTitle());
        statement.setString(3, job.getJobDescription());
        statement.setString(4, job.getRequiredSkills());
        statement.setDouble(5, job.getMinimumCgpa());
        statement.setString(6, job.getEligibleDepartment());
        statement.setInt(7, job.getGraduationYear());
        statement.setDouble(8, job.getSalaryPackage());
        statement.setString(9, job.getApplicationDeadline());
        statement.setString(10, job.getJobLocation());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

// Update job details
public boolean updateJob(Job job) {

    String sql = "UPDATE jobs SET " +
                 "company_id = ?, " +
                 "job_title = ?, " +
                 "job_description = ?, " +
                 "required_skills = ?, " +
                 "minimum_cgpa = ?, " +
                 "eligible_department = ?, " +
                 "graduation_year = ?, " +
                 "salary_package = ?, " +
                 "application_deadline = ?, " +
                 "job_location = ? " +
                 "WHERE job_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, job.getCompanyId());
        statement.setString(2, job.getJobTitle());
        statement.setString(3, job.getJobDescription());
        statement.setString(4, job.getRequiredSkills());
        statement.setDouble(5, job.getMinimumCgpa());
        statement.setString(6, job.getEligibleDepartment());
        statement.setInt(7, job.getGraduationYear());
        statement.setDouble(8, job.getSalaryPackage());
        statement.setString(9, job.getApplicationDeadline());
        statement.setString(10, job.getJobLocation());
        statement.setInt(11, job.getJobId());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

// Delete job by ID
public boolean deleteJob(int jobId) {

    String sql = "DELETE FROM jobs WHERE job_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, jobId);

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}
}