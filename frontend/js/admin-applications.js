document.addEventListener("DOMContentLoaded", function () {

    const APPLICATION_API_URL =
        "http://localhost:8080/applications";

    const STUDENT_API_URL =
        "http://localhost:8080/students";

    const JOB_API_URL =
        "http://localhost:8080/jobs";

    const COMPANY_API_URL =
        "http://localhost:8080/companies";


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


    const searchInput =
        document.getElementById("searchInput");

    const applicationsMessage =
        document.getElementById(
            "applicationsMessage"
        );

    const applicationsTableBody =
        document.getElementById(
            "applicationsTableBody"
        );


    const backButton =
        document.getElementById("backButton");

    const logoutButton =
        document.getElementById("logoutButton");


    let allApplications = [];
    let students = [];
    let jobs = [];
    let companies = [];


    // ==========================================
    // ESCAPE HTML
    // ==========================================

    function escapeHtml(value) {

        return String(value ?? "")
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }


    // ==========================================
    // LOAD STUDENTS
    // ==========================================

    async function loadStudents() {

        try {

            const response =
                await fetch(STUDENT_API_URL);

            if (!response.ok) {
                throw new Error(
                    "Could not load students."
                );
            }


            const text =
                await response.text();


            students = [];


            const lines =
                text.split(/\r?\n/);


            lines.forEach(function (line) {

                const trimmed =
                    line.trim();


                if (
                    !trimmed ||
                    trimmed.startsWith("=")
                ) {
                    return;
                }


                const parts =
                    trimmed.split("|");


                if (parts.length >= 5) {

                    students.push({

                        studentId:
                            Number(parts[0].trim()),

                        fullName:
                            parts[1].trim(),

                        email:
                            parts[2].trim(),

                        department:
                            parts[3].trim(),

                        cgpa:
                            Number(parts[4].trim())
                    });
                }

            });

        } catch (error) {

            console.error(
                "Student loading error:",
                error
            );

            students = [];
        }
    }


    // ==========================================
    // LOAD JOBS
    // ==========================================

    async function loadJobs() {

        try {

            const response =
                await fetch(JOB_API_URL);

            if (!response.ok) {
                throw new Error(
                    "Could not load jobs."
                );
            }


            jobs =
                await response.json();

        } catch (error) {

            console.error(
                "Job loading error:",
                error
            );

            jobs = [];
        }
    }


    // ==========================================
    // LOAD COMPANIES
    // ==========================================

    async function loadCompanies() {

        try {

            const response =
                await fetch(COMPANY_API_URL);

            if (!response.ok) {
                throw new Error(
                    "Could not load companies."
                );
            }


            companies =
                await response.json();

        } catch (error) {

            console.error(
                "Company loading error:",
                error
            );

            companies = [];
        }
    }


    // ==========================================
    // LOAD APPLICATIONS
    // ==========================================

    async function loadApplications() {

        try {

            applicationsMessage.textContent =
                "Loading applications...";

            applicationsMessage.className =
                "message info";


            const response =
                await fetch(
                    APPLICATION_API_URL
                );


            if (!response.ok) {
                throw new Error(
                    "Could not load applications."
                );
            }


            allApplications =
                await response.json();


            updateSummary(
                allApplications
            );


            displayApplications(
                allApplications
            );


            applicationsMessage.textContent =
                "";

            applicationsMessage.className =
                "message";

        } catch (error) {

            console.error(error);

            applicationsMessage.textContent =
                "Could not load applications. Please make sure the Java backend is running.";

            applicationsMessage.className =
                "message error";
        }
    }


    // ==========================================
    // UPDATE SUMMARY CARDS
    // ==========================================

    function updateSummary(applications) {

        let applied = 0;
        let shortlisted = 0;
        let selected = 0;
        let rejected = 0;


        applications.forEach(
            function (application) {

                const status =
                    String(
                        application.status || ""
                    ).toLowerCase();


                if (status === "applied") {

                    applied++;

                } else if (
                    status === "shortlisted"
                ) {

                    shortlisted++;

                } else if (
                    status === "selected"
                ) {

                    selected++;

                } else if (
                    status === "rejected"
                ) {

                    rejected++;
                }

            }
        );


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
    }


    // ==========================================
    // GET STUDENT NAME
    // ==========================================

    function getStudentName(studentId) {

        const student =
            students.find(
                function (item) {

                    return item.studentId ===
                        studentId;
                }
            );


        return student
            ? student.fullName
            : "Student #" + studentId;
    }


    // ==========================================
    // GET JOB
    // ==========================================

    function getJob(jobId) {

        return jobs.find(
            function (job) {

                return job.jobId === jobId;
            }
        );
    }


    // ==========================================
    // GET JOB TITLE
    // ==========================================

    function getJobTitle(jobId) {

        const job =
            getJob(jobId);


        return job
            ? job.jobTitle
            : "Job #" + jobId;
    }


    // ==========================================
    // GET COMPANY NAME
    // ==========================================

    function getCompanyName(jobId) {

        const job =
            getJob(jobId);


        if (!job) {
            return "Unknown Company";
        }


        const company =
            companies.find(
                function (item) {

                    return item.companyId ===
                        job.companyId;
                }
            );


        return company
            ? company.companyName
            : "Unknown Company";
    }


    // ==========================================
    // GET STATUS CLASS
    // ==========================================

    function getStatusClass(status) {

        const normalized =
            String(status || "")
                .toLowerCase();


        if (normalized === "applied") {
            return "status-applied";
        }


        if (normalized === "shortlisted") {
            return "status-shortlisted";
        }


        if (normalized === "selected") {
            return "status-selected";
        }


        if (normalized === "rejected") {
            return "status-rejected";
        }


        return "";
    }


    // ==========================================
    // DISPLAY APPLICATIONS
    // ==========================================

    function displayApplications(
        applications
    ) {

        applicationsTableBody.innerHTML =
            "";


        if (applications.length === 0) {

            applicationsTableBody.innerHTML = `
                <tr>
                    <td colspan="8">
                        No applications found.
                    </td>
                </tr>
            `;

            return;
        }


        applications.forEach(
            function (application) {

                const row =
                    document.createElement("tr");


                const studentName =
                    getStudentName(
                        application.studentId
                    );


                const jobTitle =
                    getJobTitle(
                        application.jobId
                    );


                const companyName =
                    getCompanyName(
                        application.jobId
                    );


                const status =
                    application.status || "";


                const statusClass =
                    getStatusClass(status);


                row.innerHTML = `
                    <td>
                        ${application.applicationId}
                    </td>

                    <td>
                        ${escapeHtml(studentName)}
                    </td>

                    <td>
                        ${escapeHtml(jobTitle)}
                    </td>

                    <td>
                        ${escapeHtml(companyName)}
                    </td>

                    <td>
                        ${escapeHtml(
                            application.applicationDate || ""
                        )}
                    </td>

                    <td>
                        <span class="status-badge ${statusClass}">
                            ${escapeHtml(status)}
                        </span>
                    </td>

                    <td>
                        ${escapeHtml(
                            application.remarks || ""
                        )}
                    </td>

                    <td>
                        <div class="action-buttons">

                            <button
                                class="edit-button"
                                data-id="${application.applicationId}">
                                Edit
                            </button>

                            <button
                                class="delete-button"
                                data-id="${application.applicationId}">
                                Delete
                            </button>

                        </div>
                    </td>
                `;


                applicationsTableBody.appendChild(
                    row
                );

            }
        );


        attachActionButtons();
    }


    // ==========================================
    // ATTACH BUTTON EVENTS
    // ==========================================

    function attachActionButtons() {

        const editButtons =
            document.querySelectorAll(
                ".edit-button"
            );


        editButtons.forEach(
            function (button) {

                button.addEventListener(
                    "click",
                    function () {

                        const applicationId =
                            Number(
                                button.dataset.id
                            );


                        editApplication(
                            applicationId
                        );
                    }
                );

            }
        );


        const deleteButtons =
            document.querySelectorAll(
                ".delete-button"
            );


        deleteButtons.forEach(
            function (button) {

                button.addEventListener(
                    "click",
                    function () {

                        const applicationId =
                            Number(
                                button.dataset.id
                            );


                        deleteApplication(
                            applicationId
                        );
                    }
                );

            }
        );
    }


    // ==========================================
    // EDIT APPLICATION
    // ==========================================

    async function editApplication(
        applicationId
    ) {

        const application =
            allApplications.find(
                function (item) {

                    return item.applicationId ===
                        applicationId;
                }
            );


        if (!application) {

            alert(
                "Application not found."
            );

            return;
        }


        const studentName =
            getStudentName(
                application.studentId
            );


        const jobTitle =
            getJobTitle(
                application.jobId
            );


        const companyName =
            getCompanyName(
                application.jobId
            );


        // ======================================
        // SELECT NEW STATUS
        // ======================================

        const newStatus =
            prompt(
                "Application Status\n\n" +
                "Allowed values:\n" +
                "Applied\n" +
                "Shortlisted\n" +
                "Selected\n" +
                "Rejected\n\n" +
                "Current status: " +
                application.status,

                application.status || "Applied"
            );


        if (newStatus === null) {
            return;
        }


        const cleanStatus =
            newStatus.trim();


        const validStatuses = [
            "Applied",
            "Shortlisted",
            "Selected",
            "Rejected"
        ];


        const matchedStatus =
            validStatuses.find(
                function (status) {

                    return status.toLowerCase() ===
                        cleanStatus.toLowerCase();
                }
            );


        if (!matchedStatus) {

            alert(
                "Invalid status.\n\n" +
                "Please use: Applied, Shortlisted, Selected, or Rejected."
            );

            return;
        }


        // ======================================
        // ENTER REMARKS
        // ======================================

        const newRemarks =
            prompt(
                "Remarks for:\n" +
                studentName +
                " - " +
                jobTitle +
                " (" +
                companyName +
                ")",

                application.remarks || ""
            );


        if (newRemarks === null) {
            return;
        }


        const updatedData = {

            applicationDate:
                application.applicationDate || "",

            status:
                matchedStatus,

            remarks:
                newRemarks.trim()
        };


        try {

            const response =
                await fetch(
                    APPLICATION_API_URL +
                    "?applicationId=" +
                    applicationId,
                    {
                        method: "PUT",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(
                                updatedData
                            )
                    }
                );


            const result =
                await response.text();


            if (!response.ok) {

                throw new Error(result);
            }


            applicationsMessage.textContent =
                result;

            applicationsMessage.className =
                "message success";


            await loadApplications();


        } catch (error) {

            console.error(error);

            applicationsMessage.textContent =
                "Update failed: " +
                error.message;

            applicationsMessage.className =
                "message error";
        }
    }


    // ==========================================
    // DELETE APPLICATION
    // ==========================================

    async function deleteApplication(
        applicationId
    ) {

        const application =
            allApplications.find(
                function (item) {

                    return item.applicationId ===
                        applicationId;
                }
            );


        if (!application) {

            alert(
                "Application not found."
            );

            return;
        }


        const studentName =
            getStudentName(
                application.studentId
            );


        const jobTitle =
            getJobTitle(
                application.jobId
            );


        const confirmed =
            confirm(
                "Are you sure you want to delete this application?\n\n" +
                "Student: " +
                studentName +
                "\nJob: " +
                jobTitle +
                "\nApplication ID: " +
                applicationId
            );


        if (!confirmed) {
            return;
        }


        try {

            const response =
                await fetch(
                    APPLICATION_API_URL +
                    "?applicationId=" +
                    applicationId,
                    {
                        method: "DELETE"
                    }
                );


            const result =
                await response.text();


            if (!response.ok) {

                throw new Error(result);
            }


            applicationsMessage.textContent =
                result;

            applicationsMessage.className =
                "message success";


            await loadApplications();


        } catch (error) {

            console.error(error);

            applicationsMessage.textContent =
                "Delete failed: " +
                error.message;

            applicationsMessage.className =
                "message error";
        }
    }


    // ==========================================
    // SEARCH APPLICATIONS
    // ==========================================

    searchInput.addEventListener(
        "input",
        function () {

            const searchTerm =
                searchInput.value
                    .trim()
                    .toLowerCase();


            if (searchTerm === "") {

                displayApplications(
                    allApplications
                );

                return;
            }


            const filteredApplications =
                allApplications.filter(
                    function (application) {

                        const studentName =
                            getStudentName(
                                application.studentId
                            );


                        const jobTitle =
                            getJobTitle(
                                application.jobId
                            );


                        const companyName =
                            getCompanyName(
                                application.jobId
                            );


                        return (

                            String(
                                application.applicationId
                            )
                            .toLowerCase()
                            .includes(searchTerm)

                            ||

                            studentName
                                .toLowerCase()
                                .includes(searchTerm)

                            ||

                            jobTitle
                                .toLowerCase()
                                .includes(searchTerm)

                            ||

                            companyName
                                .toLowerCase()
                                .includes(searchTerm)

                            ||

                            String(
                                application.status || ""
                            )
                            .toLowerCase()
                            .includes(searchTerm)

                            ||

                            String(
                                application.remarks || ""
                            )
                            .toLowerCase()
                            .includes(searchTerm)
                        );
                    }
                );


            displayApplications(
                filteredApplications
            );
        }
    );


    // ==========================================
    // BACK BUTTON
    // ==========================================

    backButton.addEventListener(
        "click",
        function () {

            window.location.href =
                "admin-dashboard.html";
        }
    );


    // ==========================================
    // LOGOUT
    // ==========================================

    logoutButton.addEventListener(
        "click",
        function () {

            localStorage.removeItem(
                "adminEmail"
            );


            window.location.href =
                "login.html";
        }
    );


    // ==========================================
    // INITIALIZE PAGE
    // ==========================================

    async function initializePage() {

        await Promise.all([
            loadStudents(),
            loadJobs(),
            loadCompanies()
        ]);


        await loadApplications();
    }


    initializePage();

});