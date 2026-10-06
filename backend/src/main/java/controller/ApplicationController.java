package controller;

import model.Application;
import service.ApplicationService;

import java.util.List;

public class ApplicationController {

    private ApplicationService applicationService;

    public ApplicationController() {
        applicationService = new ApplicationService();
    }

    // GET ALL APPLICATIONS
    public List<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    // GET APPLICATION BY ID
    public Application getApplicationById(int applicationId) {
        return applicationService.getApplicationById(applicationId);
    }

    // ADD APPLICATION
    public boolean addApplication(Application application) {
        return applicationService.addApplication(application);
    }

    // UPDATE APPLICATION
    public boolean updateApplication(Application application) {
        return applicationService.updateApplication(application);
    }

    // DELETE APPLICATION
    public boolean deleteApplication(int applicationId) {
        return applicationService.deleteApplication(applicationId);
    }
    // Check whether a student has already applied for a job
public boolean hasApplied(int studentId, int jobId) {

    return applicationService.hasApplied(
            studentId,
            jobId
    );
}
}