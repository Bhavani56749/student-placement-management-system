document.addEventListener("DOMContentLoaded", async function () {

    const studentEmail =
        localStorage.getItem("studentEmail");

    const applicationsContainer =
        document.getElementById("applicationsContainer");

    const backButton =
        document.getElementById("backButton");


    // ==========================================
    // CHECK LOGIN
    // ==========================================

    if (!studentEmail) {

        applicationsContainer.innerHTML = `
            <div class="message">
                Please login first.
            </div>
        `;

        return;
    }


    try {

        // ==========================================
        // GET STUDENT PROFILE
        // ==========================================

        const studentResponse =
            await fetch(
                "http://localhost:8080/student-profile?email="
                + encodeURIComponent(studentEmail)
            );


        if (!studentResponse.ok) {

            throw new Error(
                "Student profile could not be loaded."
            );

        }


        const student =
            await studentResponse.json();


        console.log(
            "Logged-in student:",
            student
        );


        // ==========================================
        // GET STUDENT APPLICATIONS
        // ==========================================

        const applicationsResponse =
            await fetch(
                "http://localhost:8080/applications?studentId="
                + student.studentId
            );


        if (!applicationsResponse.ok) {

            throw new Error(
                "Applications could not be loaded."
            );

        }


        const applications =
            await applicationsResponse.json();


        console.log(
            "Applications:",
            applications
        );


        // ==========================================
        // CHECK APPLICATIONS
        // ==========================================

        if (
            !applications ||
            applications.length === 0
        ) {

            applicationsContainer.innerHTML = `
                <div class="message">
                    You have not applied for any jobs yet.
                </div>
            `;

            return;
        }


        // ==========================================
        // GET ALL JOBS
        // ==========================================

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


        // ==========================================
        // GET ALL COMPANIES
        // ==========================================

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


        // ==========================================
        // CLEAR OLD CONTENT
        // ==========================================

        applicationsContainer.innerHTML = "";


        // ==========================================
        // DISPLAY APPLICATIONS
        // ==========================================

        applications.forEach(
            function (application) {


                // ==================================
                // FIND JOB
                // ==================================

                const job =
                    jobs.find(
                        function (item) {

                            return (
                                item.jobId ===
                                application.jobId
                            );

                        }
                    );


                // ==================================
                // DEFAULT VALUES
                // ==================================

                let jobTitle =
                    "Job ID: "
                    + application.jobId;


                let companyName =
                    "Company not available";


                let companyId =
                    null;


                // ==================================
                // GET JOB INFORMATION
                // ==================================

                if (job) {

                    jobTitle =
                        job.jobTitle;

                    companyId =
                        job.companyId;


                    // ==================================
                    // FIND COMPANY
                    // ==================================

                    const company =
                        companies.find(
                            function (item) {

                                return (
                                    item.companyId ===
                                    companyId
                                );

                            }
                        );


                    if (company) {

                        companyName =
                            company.companyName;

                    }

                }


                // ==================================
                // APPLICATION STATUS
                // ==================================

                const status =
                    application.status ||
                    "Applied";


                const statusClass =
                    getStatusClass(status);


                // ==================================
                // APPLICATION DATE
                // ==================================

                const applicationDate =
                    application.applicationDate ||
                    "Not available";


                // ==================================
                // REMARKS
                // ==================================

                const remarks =
                    application.remarks ||
                    "No remarks";


                // ==================================
                // CREATE CARD
                // ==================================

                const card =
                    document.createElement("div");


                card.className =
                    "application-card";


                card.innerHTML = `

                    <div class="job-header">

                        <div>

                            <h2 class="job-title">
                                ${jobTitle}
                            </h2>

                            <div class="company-name">
                                ${companyName}
                            </div>

                        </div>

                        <span class="status-badge ${statusClass}">
                            ${status}
                        </span>

                    </div>


                    <div class="details">


                        <div class="detail-row">

                            <span class="detail-label">
                                Application ID
                            </span>

                            <span class="detail-value">
                                ${application.applicationId}
                            </span>

                        </div>


                        <div class="detail-row">

                            <span class="detail-label">
                                Applied Date
                            </span>

                            <span class="detail-value">
                                ${applicationDate}
                            </span>

                        </div>


                        <div class="detail-row">

                            <span class="detail-label">
                                Status
                            </span>

                            <span class="detail-value">
                                ${status}
                            </span>

                        </div>


                        <div class="remarks">

                            <strong>
                                Remarks
                            </strong>

                            ${remarks}

                        </div>


                    </div>

                `;


                applicationsContainer.appendChild(
                    card
                );

            }
        );


    } catch (error) {

        console.error(
            "Applications loading error:",
            error
        );


        applicationsContainer.innerHTML = `

            <div class="message">

                Could not load applications.

                <br><br>

                Please make sure the backend
                server is running.

            </div>

        `;

    }


    // ==========================================
    // BACK TO DASHBOARD
    // ==========================================

    backButton.addEventListener(
        "click",
        function () {

            window.location.href =
                "student-dashboard.html";

        }
    );

});


// ==========================================
// STATUS CLASS
// ==========================================

function getStatusClass(status) {

    if (!status) {

        return "status-default";

    }


    const normalizedStatus =
        status
            .toLowerCase()
            .trim();


    if (
        normalizedStatus ===
        "applied"
    ) {

        return "status-applied";

    }


    if (
        normalizedStatus ===
        "shortlisted"
    ) {

        return "status-shortlisted";

    }


    if (
        normalizedStatus ===
        "rejected"
    ) {

        return "status-rejected";

    }


    if (
        normalizedStatus ===
        "selected"
    ) {

        return "status-selected";

    }


    return "status-default";

}