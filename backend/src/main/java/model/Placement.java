package model;

public class Placement {

    private int placementId;
    private int studentId;
    private int companyId;
    private int jobId;
    private String placementDate;
    private double packageAmount;
    private String placementStatus;

    public Placement() {
    }

    public Placement(int placementId, int studentId, int companyId,
                     int jobId, String placementDate,
                     double packageAmount, String placementStatus) {

        this.placementId = placementId;
        this.studentId = studentId;
        this.companyId = companyId;
        this.jobId = jobId;
        this.placementDate = placementDate;
        this.packageAmount = packageAmount;
        this.placementStatus = placementStatus;
    }

    public int getPlacementId() {
        return placementId;
    }

    public void setPlacementId(int placementId) {
        this.placementId = placementId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getPlacementDate() {
        return placementDate;
    }

    public void setPlacementDate(String placementDate) {
        this.placementDate = placementDate;
    }

    public double getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(double packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getPlacementStatus() {
        return placementStatus;
    }

    public void setPlacementStatus(String placementStatus) {
        this.placementStatus = placementStatus;
    }
}