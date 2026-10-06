document.addEventListener("DOMContentLoaded", async function () {

    const studentEmail =
        localStorage.getItem("studentEmail");

    if (!studentEmail) {

        alert("Please login first.");

        window.location.href = "login.html";

        return;
    }


    const skillsContainer =
        document.getElementById("skillsContainer");


    try {

        // ==========================================
        // GET LOGGED-IN STUDENT PROFILE
        // ==========================================

        const studentResponse =
            await fetch(
                "http://localhost:8080/student-profile?email="
                + encodeURIComponent(studentEmail)
            );


        if (!studentResponse.ok) {

            throw new Error(
                "Student profile not found."
            );

        }


        const student =
            await studentResponse.json();


        console.log(
            "Logged-in student:",
            student
        );


        // ==========================================
        // GET STUDENT-SPECIFIC SKILLS
        // ==========================================

        const skillsResponse =
            await fetch(
                "http://localhost:8080/student-skills?studentId="
+ student.studentId
            );


        if (!skillsResponse.ok) {

            throw new Error(
                "Student skills could not be loaded."
            );

        }


        const skills =
            await skillsResponse.json();


        console.log(
            "Student skills:",
            skills
        );


        // ==========================================
        // CLEAR CONTAINER
        // ==========================================

        skillsContainer.innerHTML = "";


        // ==========================================
        // NO SKILLS
        // ==========================================

        if (skills.length === 0) {

            skillsContainer.innerHTML =
                "<p>No skills found.</p>";

            return;
        }


        // ==========================================
        // DISPLAY SKILLS
        // ==========================================

        skills.forEach(function (skill) {

    const card =
        document.createElement("div");

    card.className =
        "skill-card";

    card.innerHTML = `
        <h2>${skill.skillName}</h2>
        <p><strong>Proficiency:</strong> ${skill.proficiencyLevel}</p>
    `;

    skillsContainer.appendChild(card);

});

    }
    catch (error) {

        console.error(
            "Skills loading error:",
            error
        );


        skillsContainer.innerHTML =
            "<p>Could not load skills.</p>";

    }

});