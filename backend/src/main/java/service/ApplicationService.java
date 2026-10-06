package service;

import dao.ApplicationDAO;
import model.Application;

import java.util.List;

public class ApplicationService {

    private ApplicationDAO applicationDAO;

    public ApplicationService() {
        applicationDAO = new ApplicationDAO();
    }

    // Get all applications
    public List<Application> getAllApplications() {
        return applicationDAO.getAllApplications();
    }

    // Get application by ID
public Application getApplicationById(int applicationId) {
    return applicationDAO.getApplicationById(applicationId);
}

// Add a new application
public boolean addApplication(Application application) {
    return applicationDAO.addApplication(application);
}

// Update an application
public boolean updateApplication(Application application) {
    return applicationDAO.updateApplication(application);
}

// Delete an application
public boolean deleteApplication(int applicationId) {
    return applicationDAO.deleteApplication(applicationId);
}
// Check whether a student has already applied for a job
public boolean hasApplied(int studentId, int jobId) {

    return applicationDAO.hasApplied(
            studentId,
            jobId
    );
}
}