document.addEventListener("DOMContentLoaded", async function () {

    const studentEmail =
        localStorage.getItem("studentEmail");

    const jobsContainer =
        document.getElementById("jobsContainer");

    const backButton =
        document.getElementById("backButton");


    // ==========================================
    // CHECK LOGIN
    // ==========================================

    if (!studentEmail) {

        alert("Please login first.");

        window.location.href =
            "login.html";

        return;
    }


    // ==========================================
    // BACK TO DASHBOARD
    // ==========================================

    if (backButton) {

        backButton.addEventListener(
            "click",
            function () {

                window.location.href =
                    "student-dashboard.html";

            }
        );
    }


    try {

        // ==========================================
        // GET LOGGED-IN STUDENT
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
        // GET AVAILABLE JOBS
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
            "Available jobs:",
            jobs
        );


        jobsContainer.innerHTML = "";


        // ==========================================
        // NO JOBS
        // ==========================================

        if (jobs.length === 0) {

            jobsContainer.innerHTML =
                "<p>No jobs are currently available.</p>";

            return;
        }


        // ==========================================
        // DISPLAY JOBS
        // ==========================================

        jobs.forEach(function (job) {

            const card =
                document.createElement("div");

            card.className =
                "job-card";


            card.innerHTML = `

                <h2>
                    ${job.jobTitle}
                </h2>

                <p>
                    <strong>Description:</strong>
                    ${job.jobDescription || "Not provided"}
                </p>

                <div class="required-skills">

                    <strong>
                        Required Skills:
                    </strong>

                    <br>

                    ${job.requiredSkills || "Not specified"}

                </div>

                <p>
                    <strong>
                        Minimum CGPA:
                    </strong>
                    ${job.minimumCgpa}
                </p>

                <p>
                    <strong>
                        Eligible Department:
                    </strong>
                    ${job.eligibleDepartment || "Not specified"}
                </p>

                <p>
                    <strong>
                        Graduation Year:
                    </strong>
                    ${job.graduationYear}
                </p>

                <p>
                    <strong>
                        Salary Package:
                    </strong>
                    ₹${job.salaryPackage}
                </p>

                <p>
                    <strong>
                        Application Deadline:
                    </strong>
                    ${job.applicationDeadline || "Not specified"}
                </p>

                <p>
                    <strong>
                        Location:
                    </strong>
                    ${job.jobLocation || "Not specified"}
                </p>

                <button
                    class="apply-button"
                    data-job-id="${job.jobId}">

                    Apply Now

                </button>

            `;


            jobsContainer.appendChild(card);

        });


        // ==========================================
        // APPLY BUTTONS
        // ==========================================

        const applyButtons =
            document.querySelectorAll(
                ".apply-button"
            );


        applyButtons.forEach(function (button) {

            button.addEventListener(
                "click",
                async function () {

                    const jobId =
                        this.getAttribute(
                            "data-job-id"
                        );


                    // Prevent multiple clicks
                    this.disabled = true;

                    this.textContent =
                        "Applying...";


                    try {

                        const response =
                            await fetch(
                                "http://localhost:8080/apply"
                                + "?studentId="
                                + student.studentId
                                + "&jobId="
                                + jobId,
                                {
                                    method: "POST"
                                }
                            );


                        const message =
                            await response.text();


                        // ==================================
                        // APPLICATION SUCCESSFUL
                        // ==================================

                        if (response.status === 201) {

                            alert(
                                "Application submitted successfully!"
                            );

                            this.textContent =
                                "Applied";

                            this.disabled = true;

                        }


                        // ==================================
                        // ALREADY APPLIED
                        // ==================================

                        else if (response.status === 409) {

                            alert(
                                "You have already applied for this job."
                            );

                            this.textContent =
                                "Already Applied";

                            this.disabled = true;

                        }


                        // ==================================
                        // OTHER ERROR
                        // ==================================

                        else {

                            alert(
                                message ||
                                "Application could not be submitted."
                            );

                            this.textContent =
                                "Apply Now";

                            this.disabled = false;

                        }

                    }
                    catch (error) {

                        console.error(
                            "Application error:",
                            error
                        );

                        alert(
                            "Could not connect to the server."
                        );

                        this.textContent =
                            "Apply Now";

                        this.disabled = false;

                    }

                }
            );

        });

    }
    catch (error) {

        console.error(
            "Jobs loading error:",
            error
        );


        jobsContainer.innerHTML =
            "<p>Could not load available jobs.</p>";

    }

});