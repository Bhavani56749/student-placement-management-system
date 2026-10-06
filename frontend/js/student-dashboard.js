document.addEventListener("DOMContentLoaded", async function () {

    const logoutButton =
        document.getElementById("logoutButton");

    // Get logged-in student's email
    const studentEmail =
        localStorage.getItem("studentEmail");

    if (!studentEmail) {

        alert("Please login first.");

        window.location.href = "login.html";

        return;
    }


    // ==============================
    // LOAD STUDENT PROFILE
    // ==============================

    try {

        const response = await fetch(
            "http://localhost:8080/student-profile?email="
            + encodeURIComponent(studentEmail)
        );

        if (!response.ok) {

            throw new Error(
                "Student profile not found."
            );

        }

        const student =
            await response.json();


        console.log(
            "Student profile loaded:",
            student
        );


        // ==============================
        // UPDATE WELCOME MESSAGE
        // ==============================

        const welcomeHeading =
            document.querySelector(".top-bar h1");

        if (welcomeHeading) {

            welcomeHeading.textContent =
                "Welcome, " + student.fullName + " 👋";

        }


        // ==============================
        // UPDATE SIDEBAR NAME
        // ==============================

        const studentName =
            document.querySelector(".student-name");

        if (studentName) {

            studentName.textContent =
                student.fullName;

        }


    }
    catch (error) {

        console.error(
            "Profile loading error:",
            error
        );

        alert(
            "Could not load student profile."
        );

    }


    // ==============================
    // LOGOUT
    // ==============================

    if (logoutButton) {

        logoutButton.addEventListener(
            "click",
            function () {

                const confirmLogout =
                    confirm(
                        "Are you sure you want to logout?"
                    );

                if (confirmLogout) {

                    localStorage.removeItem(
                        "studentEmail"
                    );

                    window.location.href =
                        "login.html";

                }

            }
        );

    }

});