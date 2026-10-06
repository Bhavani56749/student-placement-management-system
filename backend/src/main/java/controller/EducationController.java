package controller;

import model.Education;
import service.EducationService;

import java.util.List;

public class EducationController {

    private EducationService educationService;

    public EducationController() {
        educationService = new EducationService();
    }

    // GET ALL EDUCATION RECORDS
    public List<Education> getAllEducation() {
        return educationService.getAllEducation();
    }

    // GET EDUCATION BY ID
    public Education getEducationById(int educationId) {
        return educationService.getEducationById(educationId);
    }

    // ADD EDUCATION
    public boolean addEducation(Education education) {
        return educationService.addEducation(education);
    }

    // UPDATE EDUCATION
    public boolean updateEducation(Education education) {
        return educationService.updateEducation(education);
    }

    // DELETE EDUCATION
    public boolean deleteEducation(int educationId) {
        return educationService.deleteEducation(educationId);
    }
    // GET EDUCATION BY STUDENT ID
public List<Education> getEducationByStudentId(int studentId) {
    return educationService.getEducationByStudentId(studentId);
}
}