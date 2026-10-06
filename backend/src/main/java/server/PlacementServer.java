package server;

import com.sun.net.httpserver.HttpServer;

import controller.ApplicationController;
import controller.CompanyController;
import controller.EducationController;
import controller.StudentController;
import controller.StudentSkillController;
import controller.JobController;
import controller.AdminController;

import dao.StudentDAO;

import model.Application;
import model.Company;
import model.Education;
import model.Job;
import model.Skill;
import model.Student;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class PlacementServer {

    public static void main(String[] args) throws IOException {

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0
        );


        // ==============================
        // HEALTH CHECK
        // ==============================

        server.createContext("/health", exchange -> {

            String response =
                    "Student Placement Backend is running!";

            addCorsHeaders(exchange);

            exchange.getResponseHeaders().set(
                    "Content-Type",
                    "text/plain; charset=UTF-8"
            );

            sendResponse(
                    exchange,
                    200,
                    response
            );
        });


        // ==============================
        // GET ALL STUDENTS
        // ==============================

        server.createContext("/students", exchange -> {

            StudentDAO studentDAO =
                    new StudentDAO();

            List<Student> students =
                    studentDAO.getAllStudents();

            StringBuilder response =
                    new StringBuilder();

            response.append(
                    "===== STUDENTS FROM MYSQL =====\n\n"
            );

            for (Student student : students) {

                response.append(
                        student.getStudentId()
                        + " | "
                        + student.getFullName()
                        + " | "
                        + student.getEmail()
                        + " | "
                        + student.getDepartment()
                        + " | "
                        + student.getCgpa()
                        + "\n"
                );
            }

            addCorsHeaders(exchange);

            exchange.getResponseHeaders().set(
                    "Content-Type",
                    "text/plain; charset=UTF-8"
            );

            sendResponse(
                    exchange,
                    200,
                    response.toString()
            );
        });


        // ==============================
        // REGISTER STUDENT
        // ==============================

        server.createContext("/register", exchange -> {

            addCorsHeaders(exchange);

            // Handle browser OPTIONS request
            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("OPTIONS")) {

                exchange.sendResponseHeaders(204, -1);
                exchange.close();

                return;
            }


            // Only POST is allowed
            if (!exchange.getRequestMethod()
                    .equalsIgnoreCase("POST")) {

                sendResponse(
                        exchange,
                        405,
                        "Only POST method is allowed."
                );

                return;
            }


            // Read JSON data
            InputStream inputStream =
                    exchange.getRequestBody();

            String requestBody =
                    new String(
                            inputStream.readAllBytes(),
                            StandardCharsets.UTF_8
                    );

            System.out.println(
                    "Registration data received:"
            );

            System.out.println(requestBody);


            // ==============================
            // EXTRACT DATA FROM JSON
            // ==============================

            String fullName =
                    getJsonString(
                            requestBody,
                            "fullName"
                    );

            String email =
                    getJsonString(
                            requestBody,
                            "email"
                    );

            String phone =
                    getJsonString(
                            requestBody,
                            "phone"
                    );

            String dateOfBirth =
                    getJsonString(
                            requestBody,
                            "dateOfBirth"
                    );

            String gender =
                    getJsonString(
                            requestBody,
                            "gender"
                    );

            String department =
                    getJsonString(
                            requestBody,
                            "department"
                    );

            String graduationYearText =
                    getJsonString(
                            requestBody,
                            "graduationYear"
                    );

            String cgpaText =
                    getJsonNumber(
                            requestBody,
                            "cgpa"
                    );

            String address =
                    getJsonString(
                            requestBody,
                            "address"
                    );

            String password =
                    getJsonString(
                            requestBody,
                            "password"
                    );


            // ==============================
            // CREATE STUDENT OBJECT
            // ==============================

            Student student =
                    new Student();

            student.setFullName(fullName);
            student.setEmail(email);
            student.setPassword(password);
            student.setPhone(phone);
            student.setDateOfBirth(dateOfBirth);
            student.setGender(gender);
            student.setDepartment(department);

            student.setGraduationYear(
                    Integer.parseInt(
                            graduationYearText
                    )
            );

            student.setCgpa(
                    Double.parseDouble(
                            cgpaText
                    )
            );

            student.setAddress(address);

            // No resume uploaded during registration
            student.setResumePath(null);


            // ==============================
            // SAVE TO MYSQL
            // ==============================

            StudentController studentController =
                    new StudentController();

            boolean success =
                    studentController.addStudent(
                            student
                    );


            if (success) {

                System.out.println(
                        "Student registered successfully!"
                );

                sendResponse(
                        exchange,
                        200,
                        "Student registered successfully!"
                );

            } else {

                System.out.println(
                        "Student registration failed!"
                );

                sendResponse(
                        exchange,
                        500,
                        "Student registration failed!"
                );
            }
        });


        // ==============================
        // STUDENT LOGIN
        // ==============================

        server.createContext("/login", exchange -> {

            addCorsHeaders(exchange);

            // Handle browser OPTIONS request
            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("OPTIONS")) {

                exchange.sendResponseHeaders(204, -1);
                exchange.close();

                return;
            }


            // Only POST is allowed
            if (!exchange.getRequestMethod()
                    .equalsIgnoreCase("POST")) {

                sendResponse(
                        exchange,
                        405,
                        "Only POST method is allowed."
                );

                return;
            }


            // Read JSON data
            InputStream inputStream =
                    exchange.getRequestBody();

            String requestBody =
                    new String(
                            inputStream.readAllBytes(),
                            StandardCharsets.UTF_8
                    );

            System.out.println(
                    "Login data received:"
            );

            System.out.println(requestBody);


            // ==============================
            // EXTRACT LOGIN DATA
            // ==============================

            String email =
                    getJsonString(
                            requestBody,
                            "email"
                    );

            String password =
                    getJsonString(
                            requestBody,
                            "password"
                    );


            // ==============================
            // CHECK LOGIN
            // ==============================

            StudentController studentController =
                    new StudentController();

            Student student =
                    studentController.loginStudent(
                            email,
                            password
                    );


            if (student != null) {

                System.out.println(
                        "Student login successful!"
                );

                sendResponse(
                        exchange,
                        200,
                        "Login successful! Welcome, "
                                + student.getFullName()
                );

            } else {

                System.out.println(
                        "Invalid email or password!"
                );

                sendResponse(
                        exchange,
                        401,
                        "Invalid email or password."
                );
            }
        });
        // ==============================
// ADMIN LOGIN
// ==============================

server.createContext("/admin-login", exchange -> {

    addCorsHeaders(exchange);

    // Handle browser OPTIONS request
    if (exchange.getRequestMethod()
            .equalsIgnoreCase("OPTIONS")) {

        exchange.sendResponseHeaders(204, -1);
        exchange.close();

        return;
    }

    // Only POST is allowed
    if (!exchange.getRequestMethod()
            .equalsIgnoreCase("POST")) {

        sendResponse(
                exchange,
                405,
                "Only POST method is allowed."
        );

        return;
    }

    // Read JSON data
    InputStream inputStream =
            exchange.getRequestBody();

    String requestBody =
            new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

    System.out.println(
            "Admin login data received:"
    );

    System.out.println(requestBody);

    // Extract login data
    String email =
            getJsonString(
                    requestBody,
                    "email"
            );

    String password =
            getJsonString(
                    requestBody,
                    "password"
            );

    // Check admin login
    AdminController adminController =
            new AdminController();

    boolean validAdmin =
            adminController.loginAdmin(
                    email,
                    password
            );

    if (validAdmin) {

        System.out.println(
                "Admin login successful!"
        );

        sendResponse(
                exchange,
                200,
                "Admin login successful!"
        );

    } else {

        System.out.println(
                "Invalid admin email or password!"
        );

        sendResponse(
                exchange,
                401,
                "Invalid admin email or password."
        );
    }
});



        // ==============================
        // GET STUDENT PROFILE
        // ==============================

        server.createContext(
                "/student-profile",
                exchange -> {

                    addCorsHeaders(exchange);

                    // Handle browser OPTIONS request
                    if (exchange.getRequestMethod()
                            .equalsIgnoreCase("OPTIONS")) {

                        exchange.sendResponseHeaders(
                                204,
                                -1
                        );

                        exchange.close();

                        return;
                    }


                    // Only GET is allowed
                    if (!exchange.getRequestMethod()
                            .equalsIgnoreCase("GET")) {

                        sendResponse(
                                exchange,
                                405,
                                "Only GET method is allowed."
                        );

                        return;
                    }


                    // Get email from URL
                    String query =
                            exchange.getRequestURI()
                                    .getQuery();

                    String email = "";

                    if (query != null &&
                            query.startsWith("email=")) {

                        email =
                                query.substring(6);
                    }


                    // Get student from database
                    StudentController studentController =
                            new StudentController();

                    Student student =
                            studentController
                                    .getStudentByEmail(
                                            email
                                    );


                    if (student != null) {

                        String response =
                                "{"
                                + "\"studentId\":"
                                + student.getStudentId()
                                + ","
                                + "\"fullName\":\""
                                + student.getFullName()
                                + "\","
                                + "\"email\":\""
                                + student.getEmail()
                                + "\","
                                + "\"phone\":\""
                                + student.getPhone()
                                + "\","
                                + "\"department\":\""
                                + student.getDepartment()
                                + "\","
                                + "\"graduationYear\":"
                                + student.getGraduationYear()
                                + ","
                                + "\"cgpa\":"
                                + student.getCgpa()
                                + ","
                                + "\"address\":\""
                                + student.getAddress()
                                + "\""
                                + "}";


                        exchange.getResponseHeaders().set(
                                "Content-Type",
                                "application/json; charset=UTF-8"
                        );


                        sendResponse(
                                exchange,
                                200,
                                response
                        );

                    } else {

                        sendResponse(
                                exchange,
                                404,
                                "Student not found."
                        );
                    }
                }
        );


        // ==============================
        // GET EDUCATION BY STUDENT ID
        // ==============================

        server.createContext(
                "/education",
                exchange -> {

                    addCorsHeaders(exchange);

                    // Handle browser OPTIONS request
                    if (exchange.getRequestMethod()
                            .equalsIgnoreCase("OPTIONS")) {

                        exchange.sendResponseHeaders(
                                204,
                                -1
                        );

                        exchange.close();

                        return;
                    }


                    // Only GET is allowed
                    if (!exchange.getRequestMethod()
                            .equalsIgnoreCase("GET")) {

                        sendResponse(
                                exchange,
                                405,
                                "Only GET method is allowed."
                        );

                        return;
                    }


                    // Get student ID from URL
                    String query =
                            exchange.getRequestURI()
                                    .getQuery();

                    int studentId = 0;

                    if (query != null &&
                            query.startsWith("studentId=")) {

                        try {

                            studentId =
                                    Integer.parseInt(
                                            query.substring(10)
                                    );

                        } catch (
                                NumberFormatException e) {

                            sendResponse(
                                    exchange,
                                    400,
                                    "Invalid student ID."
                            );

                            return;
                        }
                    }


                    if (studentId <= 0) {

                        sendResponse(
                                exchange,
                                400,
                                "Student ID is required."
                        );

                        return;
                    }


                    // Get education from database
                    EducationController educationController =
                            new EducationController();

                    List<Education> educationList =
                            educationController
                                    .getEducationByStudentId(
                                            studentId
                                    );


                    StringBuilder response =
                            new StringBuilder();

                    response.append("[");


                    for (
                            int i = 0;
                            i < educationList.size();
                            i++
                    ) {

                        Education education =
                                educationList.get(i);

                        response.append("{");


                        response.append(
                                "\"educationId\":"
                                + education.getEducationId()
                                + ","
                        );


                        response.append(
                                "\"studentId\":"
                                + education.getStudentId()
                                + ","
                        );


                        response.append(
                                "\"qualification\":\""
                                + education.getQualification()
                                + "\","
                        );


                        response.append(
                                "\"institution\":\""
                                + education.getInstitution()
                                + "\","
                        );


                        response.append(
                                "\"specialization\":\""
                                + education.getSpecialization()
                                + "\","
                        );


                        response.append(
                                "\"passingYear\":"
                                + education.getPassingYear()
                                + ","
                        );


                        response.append(
                                "\"percentage\":"
                                + education.getPercentage()
                        );


                        response.append("}");


                        if (
                                i <
                                educationList.size() - 1
                        ) {

                            response.append(",");
                        }
                    }


                    response.append("]");


                    exchange.getResponseHeaders().set(
                            "Content-Type",
                            "application/json; charset=UTF-8"
                    );


                    sendResponse(
                            exchange,
                            200,
                            response.toString()
                    );
                }
        );


        // ==============================
        // GET SKILLS BY STUDENT ID
        // ==============================

        server.createContext(
                "/student-skills",
                exchange -> {

                    addCorsHeaders(exchange);

                    // Handle browser OPTIONS request
                    if (exchange.getRequestMethod()
                            .equalsIgnoreCase("OPTIONS")) {

                        exchange.sendResponseHeaders(
                                204,
                                -1
                        );

                        exchange.close();

                        return;
                    }


                    // Only GET is allowed
                    if (!exchange.getRequestMethod()
                            .equalsIgnoreCase("GET")) {

                        sendResponse(
                                exchange,
                                405,
                                "Only GET method is allowed."
                        );

                        return;
                    }


                    // Get student ID from URL
                    String query =
                            exchange.getRequestURI()
                                    .getQuery();

                    int studentId = 0;

                    if (query != null &&
                            query.startsWith("studentId=")) {

                        try {

                            studentId =
                                    Integer.parseInt(
                                            query.substring(10)
                                    );

                        } catch (
                                NumberFormatException e) {

                            sendResponse(
                                    exchange,
                                    400,
                                    "Invalid student ID."
                            );

                            return;
                        }
                    }


                    if (studentId <= 0) {

                        sendResponse(
                                exchange,
                                400,
                                "Student ID is required."
                        );

                        return;
                    }


                    // Get skills from database
                    StudentSkillController
                            studentSkillController =
                            new StudentSkillController();


                    List<Skill> skills =
                            studentSkillController
                                    .getSkillsByStudentId(
                                            studentId
                                    );


                    // Build JSON response
                    StringBuilder response =
                            new StringBuilder();

                    response.append("[");


                    for (
                            int i = 0;
                            i < skills.size();
                            i++
                    ) {

                        Skill skill =
                                skills.get(i);

                        response.append("{");


                        response.append(
                                "\"skillId\":"
                                + skill.getSkillId()
                                + ","
                        );


                        response.append(
                                "\"skillName\":\""
                                + skill.getSkillName()
                                + "\","
                        );


                        response.append(
                                "\"proficiencyLevel\":\""
                                + skill.getProficiencyLevel()
                                + "\""
                        );


                        response.append("}");


                        if (
                                i <
                                skills.size() - 1
                        ) {

                            response.append(",");
                        }
                    }


                    response.append("]");


                    exchange.getResponseHeaders().set(
                            "Content-Type",
                            "application/json; charset=UTF-8"
                    );


                    sendResponse(
                            exchange,
                            200,
                            response.toString()
                    );
                }
        );


 // ==============================
// JOBS MANAGEMENT
//
// GET    = View all jobs
// POST   = Add job
// PUT    = Update job
// DELETE = Delete job
// ==============================

server.createContext("/jobs", exchange -> {

    addCorsHeaders(exchange);

    // ==========================================
    // HANDLE OPTIONS REQUEST
    // ==========================================

    if (exchange.getRequestMethod()
            .equalsIgnoreCase("OPTIONS")) {

        exchange.sendResponseHeaders(204, -1);
        exchange.close();

        return;
    }


    // ==========================================
    // GET ALL JOBS
    // GET /jobs
    // ==========================================

    if (exchange.getRequestMethod()
            .equalsIgnoreCase("GET")) {

        JobController jobController =
                new JobController();

        List<Job> jobs =
                jobController.getAllJobs();


        StringBuilder response =
                new StringBuilder();

        response.append("[");


        for (
                int i = 0;
                i < jobs.size();
                i++
        ) {

            Job job =
                    jobs.get(i);

            response.append("{");


            response.append(
                    "\"jobId\":"
                    + job.getJobId()
                    + ","
            );


            response.append(
                    "\"companyId\":"
                    + job.getCompanyId()
                    + ","
            );


            response.append(
                    "\"jobTitle\":\""
                    + job.getJobTitle()
                    + "\","
            );


            response.append(
                    "\"jobDescription\":\""
                    + job.getJobDescription()
                    + "\","
            );


            response.append(
                    "\"requiredSkills\":\""
                    + job.getRequiredSkills()
                    + "\","
            );


            response.append(
                    "\"minimumCgpa\":"
                    + job.getMinimumCgpa()
                    + ","
            );


            response.append(
                    "\"eligibleDepartment\":\""
                    + job.getEligibleDepartment()
                    + "\","
            );


            response.append(
                    "\"graduationYear\":"
                    + job.getGraduationYear()
                    + ","
            );


            response.append(
                    "\"salaryPackage\":"
                    + job.getSalaryPackage()
                    + ","
            );


            response.append(
                    "\"applicationDeadline\":\""
                    + job.getApplicationDeadline()
                    + "\","
            );


            response.append(
                    "\"jobLocation\":\""
                    + job.getJobLocation()
                    + "\""
            );


            response.append("}");


            if (
                    i < jobs.size() - 1
            ) {

                response.append(",");
            }
        }


        response.append("]");


        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json; charset=UTF-8"
        );


        sendResponse(
                exchange,
                200,
                response.toString()
        );


        return;
    }


    // ==========================================
    // ADD NEW JOB
    // POST /jobs
    // ==========================================

    if (exchange.getRequestMethod()
            .equalsIgnoreCase("POST")) {


        InputStream inputStream =
                exchange.getRequestBody();


        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );


        System.out.println(
                "Job data received:"
        );

        System.out.println(requestBody);


        // Extract job data

        String companyIdText =
                getJsonNumber(
                        requestBody,
                        "companyId"
                );


        String jobTitle =
                getJsonString(
                        requestBody,
                        "jobTitle"
                );


        String jobDescription =
                getJsonString(
                        requestBody,
                        "jobDescription"
                );


        String requiredSkills =
                getJsonString(
                        requestBody,
                        "requiredSkills"
                );


        String minimumCgpaText =
                getJsonNumber(
                        requestBody,
                        "minimumCgpa"
                );


        String eligibleDepartment =
                getJsonString(
                        requestBody,
                        "eligibleDepartment"
                );


        String graduationYearText =
                getJsonNumber(
                        requestBody,
                        "graduationYear"
                );


        String salaryPackageText =
                getJsonNumber(
                        requestBody,
                        "salaryPackage"
                );


        String applicationDeadline =
                getJsonString(
                        requestBody,
                        "applicationDeadline"
                );


        String jobLocation =
                getJsonString(
                        requestBody,
                        "jobLocation"
                );


        try {

            int companyId =
                    Integer.parseInt(
                            companyIdText
                    );

            double minimumCgpa =
                    Double.parseDouble(
                            minimumCgpaText
                    );

            int graduationYear =
                    Integer.parseInt(
                            graduationYearText
                    );

            double salaryPackage =
                    Double.parseDouble(
                            salaryPackageText
                    );


            // Create Job object

            Job job =
                    new Job();


            job.setCompanyId(
                    companyId
            );

            job.setJobTitle(
                    jobTitle
            );

            job.setJobDescription(
                    jobDescription
            );

            job.setRequiredSkills(
                    requiredSkills
            );

            job.setMinimumCgpa(
                    minimumCgpa
            );

            job.setEligibleDepartment(
                    eligibleDepartment
            );

            job.setGraduationYear(
                    graduationYear
            );

            job.setSalaryPackage(
                    salaryPackage
            );

            job.setApplicationDeadline(
                    applicationDeadline
            );

            job.setJobLocation(
                    jobLocation
            );


            // Save job

            JobController jobController =
                    new JobController();


            boolean success =
                    jobController.addJob(
                            job
                    );


            if (success) {

                sendResponse(
                        exchange,
                        201,
                        "Job added successfully."
                );

            } else {

                sendResponse(
                        exchange,
                        500,
                        "Job could not be added."
                );
            }

        } catch (NumberFormatException e) {

            sendResponse(
                    exchange,
                    400,
                    "Invalid numeric job data."
            );
        }


        return;
    }


    // ==========================================
    // UPDATE JOB
    // PUT /jobs?jobId=1
    // ==========================================

    if (exchange.getRequestMethod()
            .equalsIgnoreCase("PUT")) {


        // Get job ID

        String query =
                exchange.getRequestURI()
                        .getQuery();


        int jobId = 0;


        if (query != null) {

            String[] parameters =
                    query.split("&");


            for (
                    String parameter :
                    parameters
            ) {

                String[] pair =
                        parameter.split("=");


                if (
                        pair.length == 2 &&
                        pair[0].equals("jobId")
                ) {

                    try {

                        jobId =
                                Integer.parseInt(
                                        pair[1]
                                );

                    } catch (
                            NumberFormatException e
                    ) {

                        sendResponse(
                                exchange,
                                400,
                                "Invalid job ID."
                        );

                        return;
                    }
                }
            }
        }


        if (jobId <= 0) {

            sendResponse(
                    exchange,
                    400,
                    "Job ID is required."
            );

            return;
        }


        // Read request body

        InputStream inputStream =
                exchange.getRequestBody();


        String requestBody =
                new String(
                        inputStream.readAllBytes(),
                        StandardCharsets.UTF_8
                );


        System.out.println(
                "Job update data received:"
        );

        System.out.println(requestBody);


        // Extract updated job data

        String companyIdText =
                getJsonNumber(
                        requestBody,
                        "companyId"
                );


        String jobTitle =
                getJsonString(
                        requestBody,
                        "jobTitle"
                );


        String jobDescription =
                getJsonString(
                        requestBody,
                        "jobDescription"
                );


        String requiredSkills =
                getJsonString(
                        requestBody,
                        "requiredSkills"
                );


        String minimumCgpaText =
                getJsonNumber(
                        requestBody,
                        "minimumCgpa"
                );


        String eligibleDepartment =
                getJsonString(
                        requestBody,
                        "eligibleDepartment"
                );


        String graduationYearText =
                getJsonNumber(
                        requestBody,
                        "graduationYear"
                );


        String salaryPackageText =
                getJsonNumber(
                        requestBody,
                        "salaryPackage"
                );


        String applicationDeadline =
                getJsonString(
                        requestBody,
                        "applicationDeadline"
                );


        String jobLocation =
                getJsonString(
                        requestBody,
                        "jobLocation"
                );


        try {

            int companyId =
                    Integer.parseInt(
                            companyIdText
                    );

            double minimumCgpa =
                    Double.parseDouble(
                            minimumCgpaText
                    );

            int graduationYear =
                    Integer.parseInt(
                            graduationYearText
                    );

            double salaryPackage =
                    Double.parseDouble(
                            salaryPackageText
                    );


            // Create updated Job object

            Job job =
                    new Job();


            job.setJobId(
                    jobId
            );

            job.setCompanyId(
                    companyId
            );

            job.setJobTitle(
                    jobTitle
            );

            job.setJobDescription(
                    jobDescription
            );

            job.setRequiredSkills(
                    requiredSkills
            );

            job.setMinimumCgpa(
                    minimumCgpa
            );

            job.setEligibleDepartment(
                    eligibleDepartment
            );

            job.setGraduationYear(
                    graduationYear
            );

            job.setSalaryPackage(
                    salaryPackage
            );

            job.setApplicationDeadline(
                    applicationDeadline
            );

            job.setJobLocation(
                    jobLocation
            );


            // Update database

            JobController jobController =
                    new JobController();


            boolean success =
                    jobController.updateJob(
                            job
                    );


            if (success) {

                sendResponse(
                        exchange,
                        200,
                        "Job updated successfully."
                );

            } else {

                sendResponse(
                        exchange,
                        404,
                        "Job could not be updated."
                );
            }

        } catch (NumberFormatException e) {

            sendResponse(
                    exchange,
                    400,
                    "Invalid numeric job data."
            );
        }


        return;
    }


    // ==========================================
    // DELETE JOB
    // DELETE /jobs?jobId=1
    // ==========================================

    if (exchange.getRequestMethod()
            .equalsIgnoreCase("DELETE")) {


        // Get job ID

        String query =
                exchange.getRequestURI()
                        .getQuery();


        int jobId = 0;


        if (query != null) {

            String[] parameters =
                    query.split("&");


            for (
                    String parameter :
                    parameters
            ) {

                String[] pair =
                        parameter.split("=");


                if (
                        pair.length == 2 &&
                        pair[0].equals("jobId")
                ) {

                    try {

                        jobId =
                                Integer.parseInt(
                                        pair[1]
                                );

                    } catch (
                            NumberFormatException e
                    ) {

                        sendResponse(
                                exchange,
                                400,
                                "Invalid job ID."
                        );

                        return;
                    }
                }
            }
        }


        if (jobId <= 0) {

            sendResponse(
                    exchange,
                    400,
                    "Job ID is required."
            );

            return;
        }


        // Delete job

        JobController jobController =
                new JobController();


        boolean success =
                jobController.deleteJob(
                        jobId
                );


        if (success) {

            sendResponse(
                    exchange,
                    200,
                    "Job deleted successfully."
            );

        } else {

            sendResponse(
                    exchange,
                    404,
                    "Job could not be deleted."
            );
        }


        return;
    }


    // ==========================================
    // UNSUPPORTED METHOD
    // ==========================================

    sendResponse(
            exchange,
            405,
            "Method not allowed."
    );

});


        // ==============================
        // COMPANIES MANAGEMENT
        //
        // GET    = View companies
        // POST   = Add company
        // PUT    = Update company
        // DELETE = Delete company
        // ==============================

        server.createContext("/companies", exchange -> {

            addCorsHeaders(exchange);


            // ==========================================
            // HANDLE OPTIONS REQUEST
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("OPTIONS")) {

                exchange.sendResponseHeaders(
                        204,
                        -1
                );

                exchange.close();

                return;
            }


            // ==========================================
            // GET ALL COMPANIES
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("GET")) {

                CompanyController companyController =
                        new CompanyController();

                List<Company> companies =
                        companyController.getAllCompanies();


                StringBuilder response =
                        new StringBuilder();

                response.append("[");


                for (
                        int i = 0;
                        i < companies.size();
                        i++
                ) {

                    Company company =
                            companies.get(i);

                    response.append("{");


                    response.append(
                            "\"companyId\":"
                            + company.getCompanyId()
                            + ","
                    );


                    response.append(
                            "\"companyName\":\""
                            + company.getCompanyName()
                            + "\","
                    );


                    response.append(
                            "\"industry\":\""
                            + company.getIndustry()
                            + "\","
                    );


                    response.append(
                            "\"location\":\""
                            + company.getLocation()
                            + "\","
                    );


                    response.append(
                            "\"website\":\""
                            + company.getWebsite()
                            + "\","
                    );


                    response.append(
                            "\"contactPerson\":\""
                            + company.getContactPerson()
                            + "\","
                    );


                    response.append(
                            "\"contactEmail\":\""
                            + company.getContactEmail()
                            + "\""
                    );


                    response.append("}");


                    if (
                            i <
                            companies.size() - 1
                    ) {

                        response.append(",");
                    }

                }


                response.append("]");


                exchange.getResponseHeaders().set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );


                sendResponse(
                        exchange,
                        200,
                        response.toString()
                );


                return;
            }


            // ==========================================
            // ADD NEW COMPANY
            // POST /companies
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("POST")) {


                // Read request body

                InputStream inputStream =
                        exchange.getRequestBody();


                String requestBody =
                        new String(
                                inputStream.readAllBytes(),
                                StandardCharsets.UTF_8
                        );


                System.out.println(
                        "Company data received:"
                );

                System.out.println(
                        requestBody
                );


                // Extract company data

                String companyName =
                        getJsonString(
                                requestBody,
                                "companyName"
                        );


                String industry =
                        getJsonString(
                                requestBody,
                                "industry"
                        );


                String location =
                        getJsonString(
                                requestBody,
                                "location"
                        );


                String website =
                        getJsonString(
                                requestBody,
                                "website"
                        );


                String contactPerson =
                        getJsonString(
                                requestBody,
                                "contactPerson"
                        );


                String contactEmail =
                        getJsonString(
                                requestBody,
                                "contactEmail"
                        );


                // Create Company object

                Company company =
                        new Company();


                company.setCompanyName(
                        companyName
                );


                company.setIndustry(
                        industry
                );


                company.setLocation(
                        location
                );


                company.setWebsite(
                        website
                );


                company.setContactPerson(
                        contactPerson
                );


                company.setContactEmail(
                        contactEmail
                );


                // Save company

                CompanyController companyController =
                        new CompanyController();


                boolean success =
                        companyController.addCompany(
                                company
                        );


                if (success) {

                    sendResponse(
                            exchange,
                            201,
                            "Company added successfully."
                    );

                } else {

                    sendResponse(
                            exchange,
                            500,
                            "Company could not be added."
                    );

                }


                return;
            }


            // ==========================================
            // UPDATE COMPANY
            // PUT /companies?companyId=1
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("PUT")) {


                // Get company ID

                String query =
                        exchange.getRequestURI()
                                .getQuery();


                int companyId = 0;


                if (query != null) {

                    String[] parameters =
                            query.split("&");


                    for (
                            String parameter :
                            parameters
                    ) {

                        String[] pair =
                                parameter.split("=");


                        if (
                                pair.length == 2 &&
                                pair[0].equals("companyId")
                        ) {

                            try {

                                companyId =
                                        Integer.parseInt(
                                                pair[1]
                                        );

                            } catch (
                                    NumberFormatException e
                            ) {

                                sendResponse(
                                        exchange,
                                        400,
                                        "Invalid company ID."
                                );

                                return;
                            }

                        }

                    }

                }


                if (companyId <= 0) {

                    sendResponse(
                            exchange,
                            400,
                            "Company ID is required."
                    );

                    return;
                }


                // Read request body

                InputStream inputStream =
                        exchange.getRequestBody();


                String requestBody =
                        new String(
                                inputStream.readAllBytes(),
                                StandardCharsets.UTF_8
                        );


                System.out.println(
                        "Company update data received:"
                );

                System.out.println(
                        requestBody
                );


                // Extract updated data

                String companyName =
                        getJsonString(
                                requestBody,
                                "companyName"
                        );


                String industry =
                        getJsonString(
                                requestBody,
                                "industry"
                        );


                String location =
                        getJsonString(
                                requestBody,
                                "location"
                        );


                String website =
                        getJsonString(
                                requestBody,
                                "website"
                        );


                String contactPerson =
                        getJsonString(
                                requestBody,
                                "contactPerson"
                        );


                String contactEmail =
                        getJsonString(
                                requestBody,
                                "contactEmail"
                        );


                // Create updated Company object

                Company company =
                        new Company();


                company.setCompanyId(
                        companyId
                );


                company.setCompanyName(
                        companyName
                );


                company.setIndustry(
                        industry
                );


                company.setLocation(
                        location
                );


                company.setWebsite(
                        website
                );


                company.setContactPerson(
                        contactPerson
                );


                company.setContactEmail(
                        contactEmail
                );


                // Update database

                CompanyController companyController =
                        new CompanyController();


                boolean success =
                        companyController.updateCompany(
                                company
                        );


                if (success) {

                    sendResponse(
                            exchange,
                            200,
                            "Company updated successfully."
                    );

                } else {

                    sendResponse(
                            exchange,
                            404,
                            "Company could not be updated."
                    );

                }


                return;
            }


            // ==========================================
            // DELETE COMPANY
            // DELETE /companies?companyId=1
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("DELETE")) {


                // Get company ID

                String query =
                        exchange.getRequestURI()
                                .getQuery();


                int companyId = 0;


                if (query != null) {

                    String[] parameters =
                            query.split("&");


                    for (
                            String parameter :
                            parameters
                    ) {

                        String[] pair =
                                parameter.split("=");


                        if (
                                pair.length == 2 &&
                                pair[0].equals("companyId")
                        ) {

                            try {

                                companyId =
                                        Integer.parseInt(
                                                pair[1]
                                        );

                            } catch (
                                    NumberFormatException e
                            ) {

                                sendResponse(
                                        exchange,
                                        400,
                                        "Invalid company ID."
                                );

                                return;
                            }

                        }

                    }

                }


                if (companyId <= 0) {

                    sendResponse(
                            exchange,
                            400,
                            "Company ID is required."
                    );

                    return;
                }


                // Delete company

                CompanyController companyController =
                        new CompanyController();


                boolean success =
                        companyController.deleteCompany(
                                companyId
                        );


                if (success) {

                    sendResponse(
                            exchange,
                            200,
                            "Company deleted successfully."
                    );

                } else {

                    sendResponse(
                            exchange,
                            404,
                            "Company could not be deleted."
                    );

                }


                return;
            }


            // ==========================================
            // UNSUPPORTED METHOD
            // ==========================================

            sendResponse(
                    exchange,
                    405,
                    "Method not allowed."
            );

        });


        // ==============================
// APPLICATIONS MANAGEMENT
//
// GET    /applications
// GET    /applications?studentId=1
// PUT    /applications?applicationId=1
// DELETE /applications?applicationId=1
// ==============================

server.createContext(
        "/applications",
        exchange -> {

            addCorsHeaders(exchange);


            // ==========================================
            // HANDLE OPTIONS REQUEST
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("OPTIONS")) {

                exchange.sendResponseHeaders(
                        204,
                        -1
                );

                exchange.close();

                return;
            }


            // ==========================================
            // GET APPLICATIONS
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("GET")) {

                String query =
                        exchange.getRequestURI()
                                .getQuery();


                int studentId = 0;


                // Read optional studentId

                if (query != null) {

                    String[] parameters =
                            query.split("&");


                    for (
                            String parameter :
                            parameters
                    ) {

                        String[] pair =
                                parameter.split("=");


                        if (
                                pair.length == 2 &&
                                pair[0].equals("studentId")
                        ) {

                            try {

                                studentId =
                                        Integer.parseInt(
                                                pair[1]
                                        );

                            } catch (
                                    NumberFormatException e
                            ) {

                                sendResponse(
                                        exchange,
                                        400,
                                        "Invalid student ID."
                                );

                                return;
                            }
                        }
                    }
                }


                // Get all applications

                ApplicationController
                        applicationController =
                        new ApplicationController();


                List<Application>
                        allApplications =
                        applicationController
                                .getAllApplications();


                StringBuilder response =
                        new StringBuilder();


                response.append("[");


                boolean firstApplication =
                        true;


                for (
                        Application application :
                        allApplications
                ) {

                    // If studentId is supplied,
                    // return only that student's applications

                    if (
                            studentId > 0 &&
                            application.getStudentId()
                                    != studentId
                    ) {

                        continue;
                    }


                    if (!firstApplication) {

                        response.append(",");
                    }


                    response.append("{");


                    response.append(
                            "\"applicationId\":"
                            + application
                                    .getApplicationId()
                            + ","
                    );


                    response.append(
                            "\"studentId\":"
                            + application
                                    .getStudentId()
                            + ","
                    );


                    response.append(
                            "\"jobId\":"
                            + application
                                    .getJobId()
                            + ","
                    );


                    response.append(
                            "\"applicationDate\":\""
                            + (
                                    application
                                            .getApplicationDate()
                                            == null
                                    ? ""
                                    : application
                                            .getApplicationDate()
                            )
                            + "\","
                    );


                    response.append(
                            "\"status\":\""
                            + (
                                    application
                                            .getStatus()
                                            == null
                                    ? ""
                                    : application
                                            .getStatus()
                            )
                            + "\","
                    );


                    response.append(
                            "\"remarks\":\""
                            + (
                                    application
                                            .getRemarks()
                                            == null
                                    ? ""
                                    : application
                                            .getRemarks()
                            )
                            + "\""
                    );


                    response.append("}");


                    firstApplication =
                            false;
                }


                response.append("]");


                exchange.getResponseHeaders().set(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                );


                sendResponse(
                        exchange,
                        200,
                        response.toString()
                );


                return;
            }


            // ==========================================
            // UPDATE APPLICATION
            // PUT /applications?applicationId=1
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("PUT")) {


                String query =
                        exchange.getRequestURI()
                                .getQuery();


                int applicationId = 0;


                if (query != null) {

                    String[] parameters =
                            query.split("&");


                    for (
                            String parameter :
                            parameters
                    ) {

                        String[] pair =
                                parameter.split("=");


                        if (
                                pair.length == 2 &&
                                pair[0].equals(
                                        "applicationId"
                                )
                        ) {

                            try {

                                applicationId =
                                        Integer.parseInt(
                                                pair[1]
                                        );

                            } catch (
                                    NumberFormatException e
                            ) {

                                sendResponse(
                                        exchange,
                                        400,
                                        "Invalid application ID."
                                );

                                return;
                            }
                        }
                    }
                }


                if (applicationId <= 0) {

                    sendResponse(
                            exchange,
                            400,
                            "Application ID is required."
                    );

                    return;
                }


                // Read request body

                InputStream inputStream =
                        exchange.getRequestBody();


                String requestBody =
                        new String(
                                inputStream.readAllBytes(),
                                StandardCharsets.UTF_8
                        );


                System.out.println(
                        "Application update data received:"
                );

                System.out.println(requestBody);


                // Extract values

                String applicationDate =
                        getJsonString(
                                requestBody,
                                "applicationDate"
                        );


                String status =
                        getJsonString(
                                requestBody,
                                "status"
                        );


                String remarks =
                        getJsonString(
                                requestBody,
                                "remarks"
                        );


                // Get existing application

                ApplicationController
                        applicationController =
                        new ApplicationController();


                Application application =
                        applicationController
                                .getApplicationById(
                                        applicationId
                                );


                if (application == null) {

                    sendResponse(
                            exchange,
                            404,
                            "Application not found."
                    );

                    return;
                }


                // Keep existing date if frontend
                // does not send one

                if (
                        applicationDate == null ||
                        applicationDate.trim().isEmpty()
                ) {

                    applicationDate =
                            application
                                    .getApplicationDate();
                }


                // Create updated application

                Application updatedApplication =
                        new Application();


                updatedApplication
                        .setApplicationId(
                                applicationId
                        );


                updatedApplication
                        .setApplicationDate(
                                applicationDate
                        );


                updatedApplication
                        .setStatus(
                                status
                        );


                updatedApplication
                        .setRemarks(
                                remarks
                        );


                boolean success =
                        applicationController
                                .updateApplication(
                                        updatedApplication
                                );


                if (success) {

                    sendResponse(
                            exchange,
                            200,
                            "Application updated successfully."
                    );

                } else {

                    sendResponse(
                            exchange,
                            500,
                            "Application could not be updated."
                    );
                }


                return;
            }


            // ==========================================
            // DELETE APPLICATION
            // DELETE /applications?applicationId=1
            // ==========================================

            if (exchange.getRequestMethod()
                    .equalsIgnoreCase("DELETE")) {


                String query =
                        exchange.getRequestURI()
                                .getQuery();


                int applicationId = 0;


                if (query != null) {

                    String[] parameters =
                            query.split("&");


                    for (
                            String parameter :
                            parameters
                    ) {

                        String[] pair =
                                parameter.split("=");


                        if (
                                pair.length == 2 &&
                                pair[0].equals(
                                        "applicationId"
                                )
                        ) {

                            try {

                                applicationId =
                                        Integer.parseInt(
                                                pair[1]
                                        );

                            } catch (
                                    NumberFormatException e
                            ) {

                                sendResponse(
                                        exchange,
                                        400,
                                        "Invalid application ID."
                                );

                                return;
                            }
                        }
                    }
                }


                if (applicationId <= 0) {

                    sendResponse(
                            exchange,
                            400,
                            "Application ID is required."
                    );

                    return;
                }


                ApplicationController
                        applicationController =
                        new ApplicationController();


                boolean success =
                        applicationController
                                .deleteApplication(
                                        applicationId
                                );


                if (success) {

                    sendResponse(
                            exchange,
                            200,
                            "Application deleted successfully."
                    );

                } else {

                    sendResponse(
                            exchange,
                            404,
                            "Application could not be deleted."
                    );
                }


                return;
            }


            // ==========================================
            // UNSUPPORTED METHOD
            // ==========================================

            sendResponse(
                    exchange,
                    405,
                    "Method not allowed."
            );

        });

        // ==============================
        // APPLY FOR JOB
        // ==============================

        server.createContext(
                "/apply",
                exchange -> {

                    addCorsHeaders(exchange);

                    // Handle browser OPTIONS request
                    if (exchange.getRequestMethod()
                            .equalsIgnoreCase("OPTIONS")) {

                        exchange.sendResponseHeaders(
                                204,
                                -1
                        );

                        exchange.close();

                        return;
                    }


                    // Only POST is allowed
                    if (!exchange.getRequestMethod()
                            .equalsIgnoreCase("POST")) {

                        sendResponse(
                                exchange,
                                405,
                                "Only POST method is allowed."
                        );

                        return;
                    }


                    // Get query parameters
                    String query =
                            exchange.getRequestURI()
                                    .getQuery();


                    int studentId = 0;
                    int jobId = 0;


                    if (query != null) {

                        String[] parameters =
                                query.split("&");


                        for (
                                String parameter :
                                parameters
                        ) {

                            String[] pair =
                                    parameter.split("=");


                            if (pair.length == 2) {


                                if (
                                        pair[0]
                                        .equals("studentId")
                                ) {

                                    studentId =
                                            Integer.parseInt(
                                                    pair[1]
                                            );

                                }


                                if (
                                        pair[0]
                                        .equals("jobId")
                                ) {

                                    jobId =
                                            Integer.parseInt(
                                                    pair[1]
                                            );

                                }
                            }
                        }
                    }


                    // Validate IDs

                    if (
                            studentId <= 0 ||
                            jobId <= 0
                    ) {

                        sendResponse(
                                exchange,
                                400,
                                "Invalid studentId or jobId."
                        );

                        return;
                    }


                    // Create controller

                    ApplicationController
                            applicationController =
                            new ApplicationController();


                    // Check duplicate application

                    if (
                            applicationController.hasApplied(
                                    studentId,
                                    jobId
                            )
                    ) {

                        sendResponse(
                                exchange,
                                409,
                                "You have already applied for this job."
                        );

                        return;
                    }


                    // Create application

                    Application application =
                            new Application();


                    application.setStudentId(
                            studentId
                    );


                    application.setJobId(
                            jobId
                    );


                    application.setApplicationDate(
                            java.time.LocalDate.now()
                                    .toString()
                    );


                    application.setStatus(
                            "Applied"
                    );


                    application.setRemarks(
                            "Application submitted"
                    );


                    // Save application

                    boolean success =
                            applicationController
                                    .addApplication(
                                            application
                                    );


                    if (success) {

                        sendResponse(
                                exchange,
                                201,
                                "Application submitted successfully."
                        );

                    } else {

                        sendResponse(
                                exchange,
                                500,
                                "Application could not be submitted."
                        );
                    }
                }
        );


        // ==============================
        // START SERVER
        // ==============================

        server.start();


        System.out.println(
                "Server started successfully!"
        );


        System.out.println(
                "Health: http://localhost:8080/health"
        );


        System.out.println(
                "Students: http://localhost:8080/students"
        );


        System.out.println(
                "Register API: http://localhost:8080/register"
        );


        System.out.println(
                "Companies: http://localhost:8080/companies"
        );
    }


    // ==============================
    // CORS HEADERS
    // ==============================

    private static void addCorsHeaders(
            com.sun.net.httpserver.HttpExchange exchange) {

        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Origin",
                "*"
        );


        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS"
        );


        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers",
                "Content-Type"
        );
    }


    // ==============================
    // SEND RESPONSE
    // ==============================

    private static void sendResponse(
            com.sun.net.httpserver.HttpExchange exchange,
            int statusCode,
            String response)
            throws IOException {


        byte[] responseBytes =
                response.getBytes(
                        StandardCharsets.UTF_8
                );


        exchange.sendResponseHeaders(
                statusCode,
                responseBytes.length
        );


        OutputStream outputStream =
                exchange.getResponseBody();


        outputStream.write(
                responseBytes
        );


        outputStream.close();
    }


    // ==============================
    // GET JSON STRING VALUE
    // ==============================

    private static String getJsonString(
            String json,
            String key) {


        String search =
                "\"" + key + "\":\"";


        int start =
                json.indexOf(search);


        if (start == -1) {

            return "";
        }


        start += search.length();


        int end =
                json.indexOf(
                        "\"",
                        start
                );


        if (end == -1) {

            return "";
        }


        return json.substring(
                start,
                end
        );
    }


    // ==============================
    // GET JSON NUMBER VALUE
    // ==============================

    private static String getJsonNumber(
            String json,
            String key) {


        String search =
                "\"" + key + "\":";


        int start =
                json.indexOf(search);


        if (start == -1) {

            return "0";
        }


        start += search.length();


        int end =
                json.indexOf(
                        ",",
                        start
                );


        if (end == -1) {

            end =
                    json.indexOf(
                            "}",
                            start
                    );
        }


        return json.substring(
                start,
                end
        ).trim();
    }
}