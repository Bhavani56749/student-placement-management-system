document.addEventListener("DOMContentLoaded", function () {

    const studentCount =
        document.getElementById("studentCount");

    const companyCount =
        document.getElementById("companyCount");

    const jobCount =
        document.getElementById("jobCount");

    const applicationCount =
        document.getElementById("applicationCount");

    const logoutButton =
        document.getElementById("logoutButton");


    // ==========================================
    // LOAD DASHBOARD COUNTS
    // ==========================================

    async function loadDashboardCounts() {

        try {

            // --------------------------------------
            // LOAD STUDENTS
            // --------------------------------------

            const studentsResponse =
                await fetch(
                    "http://localhost:8080/students"
                );


            if (!studentsResponse.ok) {

                throw new Error(
                    "Students could not be loaded."
                );

            }


            const studentsText =
                await studentsResponse.text();


            console.log(
                "Students response:",
                studentsText
            );


            // The /students endpoint returns text.
            // Each student is on a separate line.
            const studentLines =
                studentsText
                    .split("\n")
                    .map(
                        function (line) {
                            return line.trim();
                        }
                    )
                    .filter(
                        function (line) {
                            return (
                                line !== "" &&
                                !line.startsWith(
                                    "===== STUDENTS FROM MYSQL ====="
                                )
                            );
                        }
                    );


            const totalStudents =
                studentLines.length;


            studentCount.textContent =
                totalStudents;


            // --------------------------------------
            // EXTRACT STUDENT IDs
            // --------------------------------------

            const studentIds =
                studentLines
                    .map(
                        function (line) {

                            const parts =
                                line.split("|");

                            if (parts.length === 0) {
                                return null;
                            }

                            const studentId =
                                parseInt(
                                    parts[0].trim()
                                );

                            return isNaN(studentId)
                                ? null
                                : studentId;
                        }
                    )
                    .filter(
                        function (id) {
                            return id !== null;
                        }
                    );


            console.log(
                "Student IDs:",
                studentIds
            );


            // --------------------------------------
            // LOAD COMPANIES
            // --------------------------------------

            const companiesResponse =
                await fetch(
                    "http://localhost:8080/companies"
                );


            if (!companiesResponse.ok) {

                throw new Error(
                    "Companies could not be loaded."
                );

            }


            const companies =
                await companiesResponse.json();


            console.log(
                "Companies:",
                companies
            );


            companyCount.textContent =
                companies.length;


            // --------------------------------------
            // LOAD JOBS
            // --------------------------------------

            const jobsResponse =
                await fetch(
                    "http://localhost:8080/jobs"
                );


            if (!jobsResponse.ok) {

                throw new Error(
                    "Jobs could not be loaded."
                );

            }


            const jobs =
                await jobsResponse.json();


            console.log(
                "Jobs:",
                jobs
            );


            jobCount.textContent =
                jobs.length;


            // --------------------------------------
            // LOAD ALL APPLICATIONS
            // --------------------------------------
            //
            // Your current backend returns
            // applications by student ID.
            //
            // So we request applications for
            // every student and add them together.
            // --------------------------------------

            let totalApplications = 0;


            const applicationRequests =
                studentIds.map(
                    async function (studentId) {

                        try {

                            const response =
                                await fetch(
                                    "http://localhost:8080/applications?studentId="
                                    + studentId
                                );


                            if (!response.ok) {

                                console.warn(
                                    "Applications could not be loaded for student:",
                                    studentId
                                );

                                return 0;
                            }


                            const applications =
                                await response.json();


                            if (
                                !Array.isArray(
                                    applications
                                )
                            ) {

                                return 0;
                            }


                            return applications.length;

                        } catch (error) {

                            console.error(
                                "Application loading error for student "
                                + studentId
                                + ":",
                                error
                            );

                            return 0;
                        }

                    }
                );


            const applicationCounts =
                await Promise.all(
                    applicationRequests
                );


            applicationCounts.forEach(
                function (count) {

                    totalApplications += count;

                }
            );


            applicationCount.textContent =
                totalApplications;


            console.log(
                "Total applications:",
                totalApplications
            );


        } catch (error) {

            console.error(
                "Admin dashboard loading error:",
                error
            );


            studentCount.textContent = "-";
            companyCount.textContent = "-";
            jobCount.textContent = "-";
            applicationCount.textContent = "-";

        }

    }


    // ==========================================
    // LOGOUT
    // ==========================================

    if (logoutButton) {

        logoutButton.addEventListener(
            "click",
            function () {

                window.location.href =
                    "login.html";

            }
        );

    }


    // ==========================================
    // START
    // ==========================================

    loadDashboardCounts();

});