package service;

import dao.StudentDAO;
import model.Student;

import java.util.List;

public class StudentService {

    private StudentDAO studentDAO;

    public StudentService() {
        studentDAO = new StudentDAO();
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    // Get student by ID
public Student getStudentById(int studentId) {
    return studentDAO.getStudentById(studentId);
}

// Add a new student
public boolean addStudent(Student student) {
    return studentDAO.addStudent(student);
}

// Update student details
public boolean updateStudent(Student student) {
    return studentDAO.updateStudent(student);
}

// Delete student by ID
public boolean deleteStudent(int studentId) {
    return studentDAO.deleteStudent(studentId);
}
// Login student
public Student loginStudent(String email, String password) {
    return studentDAO.loginStudent(email, password);
}
// Get student by email
public Student getStudentByEmail(String email) {
    return studentDAO.getStudentByEmail(email);
}
}