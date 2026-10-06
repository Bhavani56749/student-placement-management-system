package dao;

import model.Student;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Get all students
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Student student = new Student();

                student.setStudentId(resultSet.getInt("student_id"));
                student.setFullName(resultSet.getString("full_name"));
                student.setEmail(resultSet.getString("email"));
                student.setPhone(resultSet.getString("phone"));
                student.setDateOfBirth(resultSet.getString("date_of_birth"));
                student.setGender(resultSet.getString("gender"));
                student.setDepartment(resultSet.getString("department"));
                student.setGraduationYear(resultSet.getInt("graduation_year"));
                student.setCgpa(resultSet.getDouble("cgpa"));
                student.setAddress(resultSet.getString("address"));
                student.setResumePath(resultSet.getString("resume_path"));

                students.add(student);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return students;
    }

    // Get student by ID
    public Student getStudentById(int studentId) {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Student student = new Student();

                    student.setStudentId(resultSet.getInt("student_id"));
                    student.setFullName(resultSet.getString("full_name"));
                    student.setEmail(resultSet.getString("email"));
                    student.setPhone(resultSet.getString("phone"));
                    student.setDateOfBirth(resultSet.getString("date_of_birth"));
                    student.setGender(resultSet.getString("gender"));
                    student.setDepartment(resultSet.getString("department"));
                    student.setGraduationYear(resultSet.getInt("graduation_year"));
                    student.setCgpa(resultSet.getDouble("cgpa"));
                    student.setAddress(resultSet.getString("address"));
                    student.setResumePath(resultSet.getString("resume_path"));

                    return student;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    // Add a new student
public boolean addStudent(Student student) {

    String sql = "INSERT INTO students " +
             "(full_name, email, password, phone, date_of_birth, gender, " +
             "department, graduation_year, cgpa, address, resume_path) " +
             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, student.getFullName());
statement.setString(2, student.getEmail());
statement.setString(3, student.getPassword());
statement.setString(4, student.getPhone());
statement.setString(5, student.getDateOfBirth());
statement.setString(6, student.getGender());
statement.setString(7, student.getDepartment());
statement.setInt(8, student.getGraduationYear());
statement.setDouble(9, student.getCgpa());
statement.setString(10, student.getAddress());
statement.setString(11, student.getResumePath());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

// Update student details
public boolean updateStudent(Student student) {

    String sql = "UPDATE students SET " +
                 "full_name = ?, " +
                 "email = ?, " +
                 "phone = ?, " +
                 "date_of_birth = ?, " +
                 "gender = ?, " +
                 "department = ?, " +
                 "graduation_year = ?, " +
                 "cgpa = ?, " +
                 "address = ?, " +
                 "resume_path = ? " +
                 "WHERE student_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, student.getFullName());
        statement.setString(2, student.getEmail());
        statement.setString(3, student.getPhone());
        statement.setString(4, student.getDateOfBirth());
        statement.setString(5, student.getGender());
        statement.setString(6, student.getDepartment());
        statement.setInt(7, student.getGraduationYear());
        statement.setDouble(8, student.getCgpa());
        statement.setString(9, student.getAddress());
        statement.setString(10, student.getResumePath());
        statement.setInt(11, student.getStudentId());

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}

// Delete student by ID
public boolean deleteStudent(int studentId) {

    String sql = "DELETE FROM students WHERE student_id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, studentId);

        int rowsAffected = statement.executeUpdate();

        return rowsAffected > 0;

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}
// Login student
public Student loginStudent(String email, String password) {

    String sql = "SELECT * FROM students WHERE email = ? AND password = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, email);
        statement.setString(2, password);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                Student student = new Student();

                student.setStudentId(
                        resultSet.getInt("student_id")
                );

                student.setFullName(
                        resultSet.getString("full_name")
                );

                student.setEmail(
                        resultSet.getString("email")
                );

                student.setDepartment(
                        resultSet.getString("department")
                );

                student.setCgpa(
                        resultSet.getDouble("cgpa")
                );

                return student;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
// Get student by email
public Student getStudentByEmail(String email) {

    String sql = "SELECT * FROM students WHERE email = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, email);

        try (ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {

                Student student = new Student();

                student.setStudentId(
                        resultSet.getInt("student_id")
                );

                student.setFullName(
                        resultSet.getString("full_name")
                );

                student.setEmail(
                        resultSet.getString("email")
                );

                student.setPhone(
                        resultSet.getString("phone")
                );

                student.setDepartment(
                        resultSet.getString("department")
                );

                student.setGraduationYear(
                        resultSet.getInt("graduation_year")
                );

                student.setCgpa(
                        resultSet.getDouble("cgpa")
                );

                student.setAddress(
                        resultSet.getString("address")
                );

                return student;
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}
}