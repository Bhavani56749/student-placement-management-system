package model;

public class Job {

    private int jobId;
    private int companyId;
    private String jobTitle;
    private String jobDescription;
    private String requiredSkills;
    private double minimumCgpa;
    private String eligibleDepartment;
    private int graduationYear;
    private double salaryPackage;
    private String applicationDeadline;
    private String jobLocation;

    public Job() {
    }

    public Job(int jobId, int companyId, String jobTitle,
               String jobDescription, String requiredSkills,
               double minimumCgpa, String eligibleDepartment,
               int graduationYear, double salaryPackage,
               String applicationDeadline, String jobLocation) {

        this.jobId = jobId;
        this.companyId = companyId;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.requiredSkills = requiredSkills;
        this.minimumCgpa = minimumCgpa;
        this.eligibleDepartment = eligibleDepartment;
        this.graduationYear = graduationYear;
        this.salaryPackage = salaryPackage;
        this.applicationDeadline = applicationDeadline;
        this.jobLocation = jobLocation;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getRequiredSkills() {
        return requiredSkills;
    }

    public void setRequiredSkills(String requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public double getMinimumCgpa() {
        return minimumCgpa;
    }

    public void setMinimumCgpa(double minimumCgpa) {
        this.minimumCgpa = minimumCgpa;
    }

    public String getEligibleDepartment() {
        return eligibleDepartment;
    }

    public void setEligibleDepartment(String eligibleDepartment) {
        this.eligibleDepartment = eligibleDepartment;
    }

    public int getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(int graduationYear) {
        this.graduationYear = graduationYear;
    }

    public double getSalaryPackage() {
        return salaryPackage;
    }

    public void setSalaryPackage(double salaryPackage) {
        this.salaryPackage = salaryPackage;
    }

    public String getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }
}