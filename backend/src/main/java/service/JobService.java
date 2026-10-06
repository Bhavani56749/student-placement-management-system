package service;

import dao.JobDAO;
import model.Job;

import java.util.List;

public class JobService {

    private JobDAO jobDAO;

    public JobService() {
        jobDAO = new JobDAO();
    }

    // Get all jobs
    public List<Job> getAllJobs() {
        return jobDAO.getAllJobs();
    }

    // Get job by ID
public Job getJobById(int jobId) {
    return jobDAO.getJobById(jobId);
}

// Add a new job
public boolean addJob(Job job) {
    return jobDAO.addJob(job);
}

// Update job details
public boolean updateJob(Job job) {
    return jobDAO.updateJob(job);
}

// Delete job by ID
public boolean deleteJob(int jobId) {
    return jobDAO.deleteJob(jobId);
}
}