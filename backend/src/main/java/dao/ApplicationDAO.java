package dao;

import model.Application;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    public List<Application> getAllApplications() {

        List<Application> applications = new ArrayList<>();

        String sql = "SELECT * FROM applications";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Application application = new Application();

                application.setApplicationId(
                        resultSet.getInt("application_id")
                );
                application.setStudentId(
                        resultSet.getInt("student_id")
                );
                application.setJobId(
                        resultSet.getInt("job_id")
                );
                application.setApplicationDate(
                        resultSet.getString("application_date")
                );
                application.setStatus(
                        resultSet.getString("status")
                );
                application.setRemarks(
                        resultSet.getString("remarks")
                );

                applications.add(application);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return applications;
    }

    // Get application by ID
public Application getApplicationById(int applicationId) {

    String sql = "SELECT * FROM applications WHERE application_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, applicationId);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                Application application = new Application();

                application.setApplicationId(
                        resultSet.getInt("application_id")
                );
                application.setStudentId(
                        resultSet.getInt("student_id")
                );
                application.setJobId(
                        resultSet.getInt("job_id")
                );
                application.setApplicationDate(
                        resultSet.getString("application_date")
                );
                application.setStatus(
                        resultSet.getString("status")
                );
                application.setRemarks(
                        resultSet.getString("remarks")
                );

                return application;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

// Add a new application
public boolean addApplication(Application application) {

    String sql = "INSERT INTO applications " +
                 "(student_id, job_id, application_date, status, remarks) " +
                 "VALUES (?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, application.getStudentId());
        statement.setInt(2, application.getJobId());
        statement.setString(3, application.getApplicationDate());
        statement.setString(4, application.getStatus());
        statement.setString(5, application.getRemarks());

        int rowsInserted = statement.executeUpdate();

        return rowsInserted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
// Update an application
public boolean updateApplication(Application application) {

    String sql = "UPDATE applications SET " +
                 "application_date = ?, status = ?, remarks = ? " +
                 "WHERE application_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, application.getApplicationDate());
        statement.setString(2, application.getStatus());
        statement.setString(3, application.getRemarks());
        statement.setInt(4, application.getApplicationId());

        int rowsUpdated = statement.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// Delete an application
public boolean deleteApplication(int applicationId) {

    String sql = "DELETE FROM applications WHERE application_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, applicationId);

        int rowsDeleted = statement.executeUpdate();

        return rowsDeleted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
// Check whether a student has already applied for a job
public boolean hasApplied(int studentId, int jobId) {

    String sql =
            "SELECT COUNT(*) FROM applications " +
            "WHERE student_id = ? AND job_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setInt(1, studentId);
        statement.setInt(2, jobId);

        try (ResultSet resultSet =
                     statement.executeQuery()) {

            if (resultSet.next()) {

                return resultSet.getInt(1) > 0;
            }
        }

    } catch (SQLException e) {

        e.printStackTrace();
    }

    return false;
}
}