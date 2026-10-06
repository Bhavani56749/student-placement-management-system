package controller;

import model.Student;
import service.StudentService;

import java.util.List;

public class StudentController {

    private StudentService studentService;

    public StudentController() {
        studentService = new StudentService();
    }

    // GET ALL STUDENTS
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // GET STUDENT BY ID
    public Student getStudentById(int studentId) {
        return studentService.getStudentById(studentId);
    }

    // ADD STUDENT
    public boolean addStudent(Student student) {
        return studentService.addStudent(student);
    }

    // UPDATE STUDENT
    public boolean updateStudent(Student student) {
        return studentService.updateStudent(student);
    }

    // DELETE STUDENT
    public boolean deleteStudent(int studentId) {
        return studentService.deleteStudent(studentId);
    }
    // Login student
public Student loginStudent(String email, String password) {
    return studentService.loginStudent(email, password);
}
// Get student by email
public Student getStudentByEmail(String email) {
    return studentService.getStudentByEmail(email);
}
}