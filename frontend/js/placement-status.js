document.addEventListener("DOMContentLoaded", async function () {

    // ==========================================
    // GET PAGE ELEMENTS
    // ==========================================

    const overallStatus =
        document.getElementById("overallStatus");

    const statusDescription =
        document.getElementById("statusDescription");

    const totalApplications =
        document.getElementById("totalApplications");

    const appliedCount =
        document.getElementById("appliedCount");

    const shortlistedCount =
        document.getElementById("shortlistedCount");

    const selectedCount =
        document.getElementById("selectedCount");

    const rejectedCount =
        document.getElementById("rejectedCount");

    const placementContainer =
        document.getElementById("placementContainer");

    const backButton =
        document.getElementById("backButton");

    const logoutButton =
        document.getElementById("logoutButton");


    // ==========================================
    // GET LOGGED-IN STUDENT EMAIL
    // ==========================================

    const studentEmail =
        localStorage.getItem("studentEmail");


    if (!studentEmail) {

        overallStatus.textContent =
            "Login Required";

        statusDescription.textContent =
            "Please login to view your placement status.";

        placementContainer.innerHTML = `
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
        // GET APPLICATIONS
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
            "Student applications:",
            applications
        );


        // ==========================================
        // GET JOBS
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


        // ==========================================
        // GET COMPANIES
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
        // NO APPLICATIONS
        // ==========================================

        if (
            !applications ||
            applications.length === 0
        ) {

            overallStatus.textContent =
                "Not Applied";

            statusDescription.textContent =
                "You have not applied for any jobs yet.";

            totalApplications.textContent = "0";
            appliedCount.textContent = "0";
            shortlistedCount.textContent = "0";
            selectedCount.textContent = "0";
            rejectedCount.textContent = "0";


            placementContainer.innerHTML = `
                <div class="message">
                    You have not applied for any jobs yet.
                    <br><br>
                    Visit Available Jobs to explore opportunities.
                </div>
            `;

            return;
        }


        // ==========================================
        // STATUS COUNTS
        // ==========================================

        let applied = 0;
        let shortlisted = 0;
        let selected = 0;
        let rejected = 0;


        applications.forEach(
            function (application) {

                const status =
                    (application.status || "Applied")
                        .toLowerCase()
                        .trim();


                if (status === "applied") {

                    applied++;

                }
                else if (status === "shortlisted") {

                    shortlisted++;

                }
                else if (status === "selected") {

                    selected++;

                }
                else if (status === "rejected") {

                    rejected++;

                }

            }
        );


        // ==========================================
        // DISPLAY COUNTS
        // ==========================================

        totalApplications.textContent =
            applications.length;

        appliedCount.textContent =
            applied;

        shortlistedCount.textContent =
            shortlisted;

        selectedCount.textContent =
            selected;

        rejectedCount.textContent =
            rejected;


        // ==========================================
        // DETERMINE OVERALL STATUS
        // ==========================================

        let overallResult = "";
        let description = "";


        // If at least one application is selected
        if (selected > 0) {

            overallResult =
                "Selected";

            description =
                "Congratulations! You have been selected for a placement opportunity.";

        }

        // If no selection but at least one shortlisted
        else if (shortlisted > 0) {

            overallResult =
                "In Process";

            description =
                "You have been shortlisted for one or more placement opportunities.";

        }

        // If there are still active applications
        else if (applied > 0) {

            overallResult =
                "In Process";

            description =
                "Your placement applications are currently being processed.";

        }

        // If every application was rejected
        else if (
            rejected === applications.length
        ) {

            overallResult =
                "Not Selected";

            description =
                "None of your current applications resulted in selection.";

        }

        else {

            overallResult =
                "In Process";

            description =
                "Your placement applications are being processed.";

        }


        // ==========================================
        // DISPLAY OVERALL STATUS
        // ==========================================

        overallStatus.textContent =
            overallResult;

        statusDescription.textContent =
            description;


        // ==========================================
        // ADD STATUS CLASS
        // ==========================================

        overallStatus.classList.remove(
            "status-applied",
            "status-shortlisted",
            "status-selected",
            "status-rejected",
            "status-default"
        );


        if (overallResult === "Selected") {

            overallStatus.classList.add(
                "status-selected"
            );

        }

        else if (overallResult === "Not Selected") {

            overallStatus.classList.add(
                "status-rejected"
            );

        }

        else {

            overallStatus.classList.add(
                "status-shortlisted"
            );

        }


        // ==========================================
        // CLEAR APPLICATION CONTAINER
        // ==========================================

        placementContainer.innerHTML = "";


        // ==========================================
        // DISPLAY EACH APPLICATION
        // ==========================================

        applications.forEach(
            function (application) {

                // ----------------------------------
                // FIND JOB
                // ----------------------------------

                const job =
                    jobs.find(
                        function (item) {

                            return (
                                item.jobId ===
                                application.jobId
                            );

                        }
                    );


                // ----------------------------------
                // DEFAULT JOB INFORMATION
                // ----------------------------------

                let jobTitle =
                    "Job ID: "
                    + application.jobId;

                let companyName =
                    "Company not available";

                let companyId =
                    "N/A";


                // ----------------------------------
                // GET JOB + COMPANY
                // ----------------------------------

                if (job) {

                    jobTitle =
                        job.jobTitle;

                    companyId =
                        job.companyId;


                    const company =
                        companies.find(
                            function (item) {

                                return (
                                    item.companyId ===
                                    job.companyId
                                );

                            }
                        );


                    if (company) {

                        companyName =
                            company.companyName;

                    }

                }


                // ----------------------------------
                // STATUS
                // ----------------------------------

                const status =
                    application.status ||
                    "Applied";


                const statusClass =
                    getStatusClass(status);


                // ----------------------------------
                // DATE
                // ----------------------------------

                const applicationDate =
                    application.applicationDate ||
                    "Not available";


                // ----------------------------------
                // REMARKS
                // ----------------------------------

                const remarks =
                    application.remarks ||
                    "No remarks";


                // ----------------------------------
                // CREATE CARD
                // ----------------------------------

                const card =
                    document.createElement("div");


                card.className =
                    "placement-card";


                card.innerHTML = `

                    <div class="placement-card-header">

                        <div class="job-title">
                            ${jobTitle}
                        </div>

                        <div class="company-name">
                            ${companyName}
                        </div>

                        <span
                            class="status-badge ${statusClass}">
                            ${status}
                        </span>

                    </div>


                    <div class="placement-details">


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
                                Company ID
                            </span>

                            <span class="detail-value">
                                ${companyId}
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
                                Current Status
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


                        ${
                            status.toLowerCase().trim()
                            === "selected"

                            ? `

                                <div class="selected-info">

                                    <strong>
                                        Placement Confirmed
                                    </strong>

                                    You have been selected
                                    by ${companyName}
                                    for the position of
                                    ${jobTitle}.

                                </div>

                              `

                            : ""
                        }


                    </div>

                `;


                placementContainer.appendChild(
                    card
                );

            }
        );


    } catch (error) {

        console.error(
            "Placement status loading error:",
            error
        );


        overallStatus.textContent =
            "Unable to Load";

        statusDescription.textContent =
            "There was a problem loading your placement information.";


        placementContainer.innerHTML = `

            <div class="message">

                Could not load placement status.

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


    // ==========================================
    // LOGOUT
    // ==========================================

    if (logoutButton) {

        logoutButton.addEventListener(
            "click",
            function () {

                localStorage.removeItem(
                    "studentEmail"
                );

                window.location.href =
                    "login.html";

            }
        );

    }

});


// ==========================================
// STATUS CLASS FUNCTION
// ==========================================

function getStatusClass(status) {

    if (!status) {

        return "status-default";

    }


    const normalizedStatus =
        status
            .toLowerCase()
            .trim();


    if (normalizedStatus === "applied") {

        return "status-applied";

    }


    if (normalizedStatus === "shortlisted") {

        return "status-shortlisted";

    }


    if (normalizedStatus === "rejected") {

        return "status-rejected";

    }


    if (normalizedStatus === "selected") {

        return "status-selected";

    }


    return "status-default";

}