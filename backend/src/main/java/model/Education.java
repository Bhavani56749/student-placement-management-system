package model;

public class Education {

    private int educationId;
    private int studentId;
    private String qualification;
    private String institution;
    private String specialization;
    private int passingYear;
    private double percentage;

    public Education() {
    }

    public Education(int educationId, int studentId,
                     String qualification, String institution,
                     String specialization, int passingYear,
                     double percentage) {

        this.educationId = educationId;
        this.studentId = studentId;
        this.qualification = qualification;
        this.institution = institution;
        this.specialization = specialization;
        this.passingYear = passingYear;
        this.percentage = percentage;
    }

    public int getEducationId() {
        return educationId;
    }

    public void setEducationId(int educationId) {
        this.educationId = educationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getPassingYear() {
        return passingYear;
    }

    public void setPassingYear(int passingYear) {
        this.passingYear = passingYear;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}