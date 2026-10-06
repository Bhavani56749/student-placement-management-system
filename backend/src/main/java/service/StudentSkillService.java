package service;

import dao.StudentSkillDAO;
import model.Skill;

import java.util.List;

public class StudentSkillService {

    private StudentSkillDAO studentSkillDAO;

    public StudentSkillService() {
        studentSkillDAO = new StudentSkillDAO();
    }

    // Get skills for a particular student
    public List<Skill> getSkillsByStudentId(int studentId) {

        return studentSkillDAO.getSkillsByStudentId(studentId);
    }
}
