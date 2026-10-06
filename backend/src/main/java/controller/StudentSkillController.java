package controller;

import model.Skill;
import service.StudentSkillService;

import java.util.List;

public class StudentSkillController {

    private StudentSkillService studentSkillService;

    public StudentSkillController() {
        studentSkillService = new StudentSkillService();
    }

    // GET SKILLS BY STUDENT ID
    public List<Skill> getSkillsByStudentId(int studentId) {

        return studentSkillService.getSkillsByStudentId(studentId);
    }
}