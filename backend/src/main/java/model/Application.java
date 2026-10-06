package model;

public class Application {

    private int applicationId;
    private int studentId;
    private int jobId;
    private String applicationDate;
    private String status;
    private String remarks;

    public Application() {
    }

    public Application(int applicationId, int studentId, int jobId,
                       String applicationDate, String status,
                       String remarks) {

        this.applicationId = applicationId;
        this.studentId = studentId;
        this.jobId = jobId;
        this.applicationDate = applicationDate;
        this.status = status;
        this.remarks = remarks;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}