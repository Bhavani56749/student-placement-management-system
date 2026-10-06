import service.JobService;

import service.CompanyService;

import dao.StudentDAO;
import dao.CompanyDAO;
import dao.JobDAO;
import dao.ApplicationDAO;
import dao.EducationDAO;
import dao.SkillDAO;
import dao.PlacementDAO;

import model.Student;
import model.Company;
import model.Job;
import model.Application;
import model.Education;
import model.Skill;
import model.Placement;

import service.StudentService;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ===== STUDENT DAO TEST =====

        StudentDAO studentDAO = new StudentDAO();

        List<Student> students = studentDAO.getAllStudents();

        System.out.println("\n===== STUDENT LIST =====");

        for (Student student : students) {

            System.out.println(
                student.getStudentId() + " | " +
                student.getFullName() + " | " +
                student.getEmail() + " | " +
                student.getDepartment() + " | " +
                student.getCgpa()
            );
        }

        System.out.println("========================");
        System.out.println("Total students: " + students.size());


        // ===== COMPANY DAO TEST =====

        CompanyDAO companyDAO = new CompanyDAO();

        List<Company> companies = companyDAO.getAllCompanies();

        System.out.println("\n===== COMPANY LIST =====");

        for (Company company : companies) {

            System.out.println(
                company.getCompanyId() + " | " +
                company.getCompanyName() + " | " +
                company.getIndustry() + " | " +
                company.getLocation()
            );
        }

        System.out.println("========================");
        System.out.println("Total companies: " + companies.size());


        // ===== JOB DAO TEST =====

        JobDAO jobDAO = new JobDAO();

        List<Job> jobs = jobDAO.getAllJobs();

        System.out.println("\n===== JOB LIST =====");

        for (Job job : jobs) {

            System.out.println(
                job.getJobId() + " | " +
                job.getCompanyId() + " | " +
                job.getJobTitle() + " | " +
                job.getMinimumCgpa() + " | " +
                job.getSalaryPackage() + " LPA"
            );
        }

        System.out.println("========================");
        System.out.println("Total jobs: " + jobs.size());


        // ===== APPLICATION DAO TEST =====

        ApplicationDAO applicationDAO = new ApplicationDAO();

        List<Application> applications =
                applicationDAO.getAllApplications();

        System.out.println("\n===== APPLICATION LIST =====");

        for (Application application : applications) {

            System.out.println(
                application.getApplicationId() + " | " +
                application.getStudentId() + " | " +
                application.getJobId() + " | " +
                application.getStatus()
            );
        }

        System.out.println("========================");
        System.out.println(
            "Total applications: " + applications.size()
        );


        // ===== EDUCATION DAO TEST =====

        EducationDAO educationDAO = new EducationDAO();

        List<Education> educationList =
                educationDAO.getAllEducation();

        System.out.println("\n===== EDUCATION LIST =====");

        for (Education education : educationList) {

            System.out.println(
                education.getEducationId() + " | " +
                education.getStudentId() + " | " +
                education.getQualification() + " | " +
                education.getInstitution()
            );
        }

        System.out.println("========================");
        System.out.println(
            "Total education records: " + educationList.size()
        );


        // ===== SKILL DAO TEST =====

        SkillDAO skillDAO = new SkillDAO();

        List<Skill> skills = skillDAO.getAllSkills();

        System.out.println("\n===== SKILL LIST =====");

        for (Skill skill : skills) {

            System.out.println(
                skill.getSkillId() + " | " +
                skill.getSkillName()
            );
        }

        System.out.println("========================");
        System.out.println("Total skills: " + skills.size());


        // ===== PLACEMENT DAO TEST =====

        PlacementDAO placementDAO = new PlacementDAO();

        List<Placement> placements =
                placementDAO.getAllPlacements();

        System.out.println("\n===== PLACEMENT LIST =====");

        for (Placement placement : placements) {

            System.out.println(
                placement.getPlacementId() + " | " +
                placement.getStudentId() + " | " +
                placement.getCompanyId() + " | " +
                placement.getJobId() + " | " +
                placement.getPackageAmount() + " LPA | " +
                placement.getPlacementStatus()
            );
        }

        System.out.println("========================");
        System.out.println(
            "Total placements: " + placements.size()
        );

        // ===============================
// JOB CRUD TEST
// ===============================

JobService jobService = new JobService();

// Add test job
Job testJob = new Job(
        0,
        1,
        "Test Developer",
        "Temporary test job",
        "Java, SQL",
        7.0,
        "Computer Science",
        2027,
        4.50,
        "2026-12-31",
        "Hyderabad"
);

boolean jobAdded = jobService.addJob(testJob);

if (jobAdded) {
    System.out.println("Job added successfully!");
} else {
    System.out.println("Job addition failed.");
}

// Find the newly added job
List<Job> jobList = jobService.getAllJobs();

int testJobId = 0;

for (Job job : jobList) {
    if (job.getJobTitle().equals("Test Developer")) {
        testJobId = job.getJobId();
        break;
    }
}

System.out.println("Test Job ID: " + testJobId);

// Read and update job
Job jobToUpdate = jobService.getJobById(testJobId);

if (jobToUpdate != null) {

    System.out.println("Job found: "
            + jobToUpdate.getJobTitle());

    jobToUpdate.setSalaryPackage(5.00);

    boolean jobUpdated =
            jobService.updateJob(jobToUpdate);

    if (jobUpdated) {
        System.out.println("Job updated successfully!");
        System.out.println("New Salary: "
                + jobToUpdate.getSalaryPackage() + " LPA");
    } else {
        System.out.println("Job update failed.");
    }

    // Delete test job
    boolean jobDeleted =
            jobService.deleteJob(testJobId);

    if (jobDeleted) {
        System.out.println("Job deleted successfully!");
    } else {
        System.out.println("Job deletion failed.");
    }

} else {
    System.out.println("Test job not found.");
}
    }
}