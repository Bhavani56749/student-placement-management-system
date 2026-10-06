package controller;

import model.Job;
import service.JobService;

import java.util.List;

public class JobController {

    private JobService jobService;

    public JobController() {
        jobService = new JobService();
    }

    // GET ALL JOBS
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    // GET JOB BY ID
    public Job getJobById(int jobId) {
        return jobService.getJobById(jobId);
    }

    // ADD JOB
    public boolean addJob(Job job) {
        return jobService.addJob(job);
    }

    // UPDATE JOB
    public boolean updateJob(Job job) {
        return jobService.updateJob(job);
    }

    // DELETE JOB
    public boolean deleteJob(int jobId) {
        return jobService.deleteJob(jobId);
    }
}