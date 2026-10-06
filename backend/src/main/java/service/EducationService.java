package service;

import dao.EducationDAO;
import model.Education;

import java.util.List;

public class EducationService {

    private EducationDAO educationDAO;

    public EducationService() {
        educationDAO = new EducationDAO();
    }

    // Get all education records
    public List<Education> getAllEducation() {
        return educationDAO.getAllEducation();
    }

    // Get education by ID
    public Education getEducationById(int educationId) {
        return educationDAO.getEducationById(educationId);
    }

    // Add a new education record
public boolean addEducation(Education education) {
    return educationDAO.addEducation(education);
}

// Update an education record
public boolean updateEducation(Education education) {
    return educationDAO.updateEducation(education);
}

// Delete an education record
public boolean deleteEducation(int educationId) {
    return educationDAO.deleteEducation(educationId);
}
// Get education records by student ID
public List<Education> getEducationByStudentId(int studentId) {
    return educationDAO.getEducationByStudentId(studentId);
}
}