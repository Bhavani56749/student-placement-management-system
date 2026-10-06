document.addEventListener("DOMContentLoaded", function () {

    // ==========================================
    // GET PAGE ELEMENTS
    // ==========================================

    const studentsTableBody =
        document.getElementById("studentsTableBody");

    const studentsMessage =
        document.getElementById("studentsMessage");

    const totalStudents =
        document.getElementById("totalStudents");

    const searchInput =
        document.getElementById("searchInput");

    const backButton =
        document.getElementById("backButton");

    const logoutButton =
        document.getElementById("logoutButton");


    // ==========================================
    // STORE ALL STUDENTS
    // ==========================================

    let students = [];


    // ==========================================
    // LOAD STUDENTS
    // ==========================================

    async function loadStudents() {

        try {

            studentsMessage.style.display =
                "block";

            studentsMessage.textContent =
                "Loading students...";


            // ======================================
            // GET STUDENTS FROM /students
            // ======================================

            const response =
                await fetch(
                    "http://localhost:8080/students"
                );


            if (!response.ok) {

                throw new Error(
                    "Students could not be loaded."
                );

            }


            const studentsText =
                await response.text();


            console.log(
                "Students response:",
                studentsText
            );


            // ======================================
            // PARSE STUDENT LINES
            // ======================================

            const lines =
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
                                    "====="
                                )
                            );
                        }
                    );


            // ======================================
            // CONVERT TEXT TO STUDENT OBJECTS
            // ======================================

            students =
                lines.map(
                    function (line) {

                        const parts =
                            line.split("|")
                                .map(
                                    function (part) {
                                        return part.trim();
                                    }
                                );


                        return {

                            studentId:
                                parseInt(parts[0]),

                            fullName:
                                parts[1] || "Not available",

                            email:
                                parts[2] || "Not available",

                            department:
                                parts[3] || "Not available",

                            cgpa:
                                parts[4] || "Not available",

                            phone:
                                "Not available",

                            graduationYear:
                                "Not available"

                        };

                    }
                );


            console.log(
                "Basic student data:",
                students
            );


            // ======================================
            // GET EXTRA DETAILS
            // ======================================

            const profileRequests =
                students.map(
                    async function (student) {

                        try {

                            const profileResponse =
                                await fetch(
                                    "http://localhost:8080/student-profile?email="
                                    + encodeURIComponent(
                                        student.email
                                    )
                                );


                            if (!profileResponse.ok) {

                                return;

                            }


                            const profile =
                                await profileResponse.json();


                            student.phone =
                                profile.phone ||
                                "Not available";


                            student.graduationYear =
                                profile.graduationYear ||
                                "Not available";


                        } catch (error) {

                            console.warn(
                                "Could not load profile for:",
                                student.email,
                                error
                            );

                        }

                    }
                );


            await Promise.all(
                profileRequests
            );


            console.log(
                "Complete students:",
                students
            );


            // ======================================
            // DISPLAY TOTAL
            // ======================================

            totalStudents.textContent =
                students.length;


            // ======================================
            // DISPLAY STUDENTS
            // ======================================

            displayStudents(students);


        } catch (error) {

            console.error(
                "Admin students loading error:",
                error
            );


            totalStudents.textContent =
                "-";


            studentsTableBody.innerHTML =
                "";


            studentsMessage.style.display =
                "block";


            studentsMessage.innerHTML = `
                Could not load students.

                <br><br>

                Please make sure the backend
                server is running.
            `;

        }

    }


    // ==========================================
    // DISPLAY STUDENTS
    // ==========================================

    function displayStudents(studentList) {

        studentsTableBody.innerHTML =
            "";


        if (
            !studentList ||
            studentList.length === 0
        ) {

            studentsMessage.style.display =
                "block";

            studentsMessage.textContent =
                "No students found.";

            return;

        }


        studentsMessage.style.display =
            "none";


        studentList.forEach(
            function (student) {

                const row =
                    document.createElement("tr");


                row.innerHTML = `

                    <td>
                        ${student.studentId}
                    </td>

                    <td>
                        ${student.fullName}
                    </td>

                    <td>
                        ${student.email}
                    </td>

                    <td>
                        ${student.phone}
                    </td>

                    <td>
                        ${student.department}
                    </td>

                    <td>
                        ${student.graduationYear}
                    </td>

                    <td>
                        ${student.cgpa}
                    </td>

                `;


                studentsTableBody.appendChild(
                    row
                );

            }
        );

    }


    // ==========================================
    // SEARCH STUDENTS
    // ==========================================

    searchInput.addEventListener(
        "input",
        function () {

            const searchText =
                searchInput.value
                    .toLowerCase()
                    .trim();


            const filteredStudents =
                students.filter(
                    function (student) {

                        return (

                            String(
                                student.studentId
                            )
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                            ||

                            student.fullName
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                            ||

                            student.email
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                            ||

                            student.department
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                        );

                    }
                );


            displayStudents(
                filteredStudents
            );

        }
    );


    // ==========================================
    // BACK TO ADMIN DASHBOARD
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

    loadStudents();

});