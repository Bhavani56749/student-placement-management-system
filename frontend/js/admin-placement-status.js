document.addEventListener("DOMContentLoaded", function () {

    const STUDENT_API_URL =
        "http://localhost:8080/students";

    const APPLICATION_API_URL =
        "http://localhost:8080/applications";


    const totalStudents =
        document.getElementById("totalStudents");

    const placedStudents =
        document.getElementById("placedStudents");

    const inProcessStudents =
        document.getElementById("inProcessStudents");

    const notSelectedStudents =
        document.getElementById("notSelectedStudents");

    const noApplicationStudents =
        document.getElementById("noApplicationStudents");


    const searchInput =
        document.getElementById("searchInput");

    const statusMessage =
        document.getElementById("statusMessage");

    const statusTableBody =
        document.getElementById("statusTableBody");


    const backButton =
        document.getElementById("backButton");

    const logoutButton =
        document.getElementById("logoutButton");


    let students = [];
    let applications = [];
    let studentStatusData = [];


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
    }


    // ==========================================
    // LOAD APPLICATIONS
    // ==========================================

    async function loadApplications() {

        const response =
            await fetch(
                APPLICATION_API_URL
            );


        if (!response.ok) {

            throw new Error(
                "Could not load applications."
            );
        }


        applications =
            await response.json();
    }


    // ==========================================
    // GET APPLICATIONS FOR STUDENT
    // ==========================================

    function getStudentApplications(
        studentId
    ) {

        return applications.filter(
            function (application) {

                return application.studentId ===
                    studentId;
            }
        );
    }


    // ==========================================
    // CALCULATE OVERALL STATUS
    // ==========================================

    function calculateOverallStatus(
        studentApplications
    ) {

        if (
            studentApplications.length === 0
        ) {

            return "No Applications";
        }


        const statuses =
            studentApplications.map(
                function (application) {

                    return String(
                        application.status || ""
                    ).toLowerCase();
                }
            );


        // Selected has highest priority

        if (
            statuses.includes("selected")
        ) {

            return "Placed";
        }


        // Any Applied or Shortlisted application
        // means the student is still in process

        if (
            statuses.includes("shortlisted") ||
            statuses.includes("applied")
        ) {

            return "In Process";
        }


        // Applications exist and all are rejected

        if (
            statuses.length > 0 &&
            statuses.every(
                function (status) {
                    return status === "rejected";
                }
            )
        ) {

            return "Not Selected";
        }


        return "In Process";
    }


    // ==========================================
    // BUILD STATUS DATA
    // ==========================================

    function buildStudentStatusData() {

        studentStatusData = [];


        students.forEach(
            function (student) {

                const studentApplications =
                    getStudentApplications(
                        student.studentId
                    );


                let applied = 0;
                let shortlisted = 0;
                let selected = 0;
                let rejected = 0;


                studentApplications.forEach(
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


                const overallStatus =
                    calculateOverallStatus(
                        studentApplications
                    );


                studentStatusData.push({

                    studentId:
                        student.studentId,

                    fullName:
                        student.fullName,

                    department:
                        student.department,

                    cgpa:
                        student.cgpa,

                    totalApplications:
                        studentApplications.length,

                    applied:
                        applied,

                    shortlisted:
                        shortlisted,

                    selected:
                        selected,

                    rejected:
                        rejected,

                    overallStatus:
                        overallStatus
                });

            }
        );
    }


    // ==========================================
    // UPDATE SUMMARY CARDS
    // ==========================================

    function updateSummary() {

        let placed = 0;
        let inProcess = 0;
        let notSelected = 0;
        let noApplications = 0;


        studentStatusData.forEach(
            function (student) {

                if (
                    student.overallStatus ===
                    "Placed"
                ) {

                    placed++;

                } else if (
                    student.overallStatus ===
                    "In Process"
                ) {

                    inProcess++;

                } else if (
                    student.overallStatus ===
                    "Not Selected"
                ) {

                    notSelected++;

                } else if (
                    student.overallStatus ===
                    "No Applications"
                ) {

                    noApplications++;
                }
            }
        );


        totalStudents.textContent =
            studentStatusData.length;

        placedStudents.textContent =
            placed;

        inProcessStudents.textContent =
            inProcess;

        notSelectedStudents.textContent =
            notSelected;

        noApplicationStudents.textContent =
            noApplications;
    }


    // ==========================================
    // GET STATUS CLASS
    // ==========================================

    function getStatusClass(status) {

        if (status === "Placed") {

            return "status-placed";
        }


        if (status === "In Process") {

            return "status-in-process";
        }


        if (status === "Not Selected") {

            return "status-not-selected";
        }


        return "status-no-application";
    }


    // ==========================================
    // DISPLAY TABLE
    // ==========================================

    function displayStatusData(
        data
    ) {

        statusTableBody.innerHTML =
            "";


        if (data.length === 0) {

            statusTableBody.innerHTML = `
                <tr>
                    <td colspan="10">
                        No students found.
                    </td>
                </tr>
            `;

            return;
        }


        data.forEach(
            function (student) {

                const row =
                    document.createElement("tr");


                const statusClass =
                    getStatusClass(
                        student.overallStatus
                    );


                row.innerHTML = `

                    <td>
                        ${student.studentId}
                    </td>

                    <td>
                        ${escapeHtml(
                            student.fullName
                        )}
                    </td>

                    <td>
                        ${escapeHtml(
                            student.department
                        )}
                    </td>

                    <td>
                        ${student.cgpa.toFixed(2)}
                    </td>

                    <td>
                        ${student.totalApplications}
                    </td>

                    <td>
                        ${student.applied}
                    </td>

                    <td>
                        ${student.shortlisted}
                    </td>

                    <td>
                        ${student.selected}
                    </td>

                    <td>
                        ${student.rejected}
                    </td>

                    <td>
                        <span class="status-badge ${statusClass}">
                            ${escapeHtml(
                                student.overallStatus
                            )}
                        </span>
                    </td>

                `;


                statusTableBody.appendChild(row);
            }
        );
    }


    // ==========================================
    // SEARCH
    // ==========================================

    searchInput.addEventListener(
        "input",
        function () {

            const searchTerm =
                searchInput.value
                    .trim()
                    .toLowerCase();


            if (searchTerm === "") {

                displayStatusData(
                    studentStatusData
                );

                return;
            }


            const filteredData =
                studentStatusData.filter(
                    function (student) {

                        return (

                            String(
                                student.studentId
                            )
                            .toLowerCase()
                            .includes(searchTerm)

                            ||

                            student.fullName
                                .toLowerCase()
                                .includes(searchTerm)

                            ||

                            student.department
                                .toLowerCase()
                                .includes(searchTerm)

                            ||

                            student.overallStatus
                                .toLowerCase()
                                .includes(searchTerm)
                        );
                    }
                );


            displayStatusData(
                filteredData
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

        try {

            statusMessage.textContent =
                "Loading placement status...";

            statusMessage.className =
                "message info";


            await Promise.all([
                loadStudents(),
                loadApplications()
            ]);


            buildStudentStatusData();

            updateSummary();

            displayStatusData(
                studentStatusData
            );


            statusMessage.textContent =
                "";

            statusMessage.className =
                "message";

        } catch (error) {

            console.error(error);

            statusMessage.textContent =
                "Could not load placement status. Please make sure the Java backend is running.";

            statusMessage.className =
                "message error";
        }
    }


    initializePage();

});