package dao;

import model.Education;
import util.DatabaseConnection;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EducationDAO {

    public List<Education> getAllEducation() {

        List<Education> educationList = new ArrayList<>();

        String sql = "SELECT * FROM education";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Education education = new Education();

                education.setEducationId(
                        resultSet.getInt("education_id")
                );
                education.setStudentId(
                        resultSet.getInt("student_id")
                );
                education.setQualification(
                        resultSet.getString("qualification")
                );
                education.setInstitution(
                        resultSet.getString("institution")
                );
                education.setSpecialization(
                        resultSet.getString("specialization")
                );
                education.setPassingYear(
                        resultSet.getInt("passing_year")
                );
                education.setPercentage(
                        resultSet.getDouble("percentage")
                );

                educationList.add(education);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return educationList;
    }

    // Get education by ID
public Education getEducationById(int educationId) {

    String sql = "SELECT * FROM education WHERE education_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, educationId);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {

            Education education = new Education();

            education.setEducationId(
                    resultSet.getInt("education_id"));

            education.setStudentId(
                    resultSet.getInt("student_id"));

            education.setQualification(
                    resultSet.getString("qualification"));

            education.setInstitution(
                    resultSet.getString("institution"));

            education.setSpecialization(
                    resultSet.getString("specialization"));

            education.setPassingYear(
                    resultSet.getInt("passing_year"));

            education.setPercentage(
                    resultSet.getDouble("percentage"));

            return education;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}

// Add a new education record
public boolean addEducation(Education education) {

    String sql = "INSERT INTO education " +
                 "(student_id, qualification, institution, specialization, passing_year, percentage) " +
                 "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, education.getStudentId());
        statement.setString(2, education.getQualification());
        statement.setString(3, education.getInstitution());
        statement.setString(4, education.getSpecialization());
        statement.setInt(5, education.getPassingYear());
        statement.setDouble(6, education.getPercentage());

        int rowsInserted = statement.executeUpdate();

        return rowsInserted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// Update an education record
public boolean updateEducation(Education education) {

    String sql = "UPDATE education SET " +
                 "qualification = ?, institution = ?, specialization = ?, " +
                 "passing_year = ?, percentage = ? " +
                 "WHERE education_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, education.getQualification());
        statement.setString(2, education.getInstitution());
        statement.setString(3, education.getSpecialization());
        statement.setInt(4, education.getPassingYear());
        statement.setDouble(5, education.getPercentage());
        statement.setInt(6, education.getEducationId());

        int rowsUpdated = statement.executeUpdate();

        return rowsUpdated > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

// Delete an education record
public boolean deleteEducation(int educationId) {

    String sql = "DELETE FROM education WHERE education_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, educationId);

        int rowsDeleted = statement.executeUpdate();

        return rowsDeleted > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
// Get education records by student ID
public List<Education> getEducationByStudentId(int studentId) {

    List<Education> educationList = new ArrayList<>();

    String sql = "SELECT * FROM education WHERE student_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, studentId);

        try (ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Education education = new Education();

                education.setEducationId(
                        resultSet.getInt("education_id")
                );

                education.setStudentId(
                        resultSet.getInt("student_id")
                );

                education.setQualification(
                        resultSet.getString("qualification")
                );

                education.setInstitution(
                        resultSet.getString("institution")
                );

                education.setSpecialization(
                        resultSet.getString("specialization")
                );

                education.setPassingYear(
                        resultSet.getInt("passing_year")
                );

                education.setPercentage(
                        resultSet.getDouble("percentage")
                );

                educationList.add(education);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return educationList;
}
}