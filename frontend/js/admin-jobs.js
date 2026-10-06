document.addEventListener("DOMContentLoaded", function () {

    const API_URL = "http://localhost:8080/jobs";
    const COMPANY_API_URL = "http://localhost:8080/companies";

    const jobForm = document.getElementById("jobForm");

    const companySelect =
        document.getElementById("companyId");

    const jobTitle =
        document.getElementById("jobTitle");

    const jobLocation =
        document.getElementById("jobLocation");

    const salaryPackage =
        document.getElementById("salaryPackage");

    const minimumCgpa =
        document.getElementById("minimumCgpa");

    const eligibleDepartment =
        document.getElementById("eligibleDepartment");

    const graduationYear =
        document.getElementById("graduationYear");

    const applicationDeadline =
        document.getElementById("applicationDeadline");

    const requiredSkills =
        document.getElementById("requiredSkills");

    const jobDescription =
        document.getElementById("jobDescription");

    const formMessage =
        document.getElementById("formMessage");

    const searchInput =
        document.getElementById("searchInput");

    const totalJobs =
        document.getElementById("totalJobs");

    const jobsMessage =
        document.getElementById("jobsMessage");

    const jobsTable =
        document.getElementById("jobsTable");

    const jobsTableBody =
        document.getElementById("jobsTableBody");

    const backButton =
        document.getElementById("backButton");

    const logoutButton =
        document.getElementById("logoutButton");


    let allJobs = [];
    let companies = [];
    let editingJobId = null;


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

            companySelect.innerHTML =
                '<option value="">Select Company</option>';


            companies.forEach(function (company) {

                const option =
                    document.createElement("option");

                option.value =
                    company.companyId;

                option.textContent =
                    company.companyName;

                companySelect.appendChild(option);

            });

        } catch (error) {

            console.error(error);

            companySelect.innerHTML =
                '<option value="">Unable to load companies</option>';

            formMessage.textContent =
                "Could not load companies. Please make sure the backend is running.";

            formMessage.className =
                "message error";
        }
    }


    // ==========================================
    // LOAD ALL JOBS
    // ==========================================

    async function loadJobs() {

        try {

            jobsMessage.textContent =
                "Loading jobs...";

            const response =
                await fetch(API_URL);

            if (!response.ok) {
                throw new Error(
                    "Could not load jobs."
                );
            }

            allJobs =
                await response.json();

            totalJobs.textContent =
                allJobs.length;

            displayJobs(allJobs);

            jobsMessage.textContent = "";

        } catch (error) {

            console.error(error);

            jobsMessage.textContent =
                "Could not load jobs. Please make sure the backend is running.";

            jobsMessage.className =
                "message error";
        }
    }


    // ==========================================
    // DISPLAY JOBS
    // ==========================================

    function displayJobs(jobs) {

        jobsTableBody.innerHTML = "";


        if (jobs.length === 0) {

            jobsTableBody.innerHTML = `
                <tr>
                    <td colspan="10">
                        No jobs found.
                    </td>
                </tr>
            `;

            return;
        }


        jobs.forEach(function (job) {

            const row =
                document.createElement("tr");


            const company =
                companies.find(function (item) {
                    return item.companyId === job.companyId;
                });


            const companyName =
                company
                    ? company.companyName
                    : "Unknown Company";


            row.innerHTML = `
                <td>${job.jobId}</td>

                <td>${escapeHtml(companyName)}</td>

                <td>${escapeHtml(job.jobTitle || "")}</td>

                <td>${escapeHtml(job.jobLocation || "")}</td>

                <td>${job.salaryPackage ?? ""}</td>

                <td>${job.minimumCgpa ?? ""}</td>

                <td>${escapeHtml(job.eligibleDepartment || "")}</td>

                <td>${job.graduationYear ?? ""}</td>

                <td>${escapeHtml(job.applicationDeadline || "")}</td>

                <td class="action-buttons">

                    <button
                        class="edit-btn"
                        data-id="${job.jobId}">
                        Edit
                    </button>

                    <button
                        class="delete-btn"
                        data-id="${job.jobId}">
                        Delete
                    </button>

                </td>
            `;


            jobsTableBody.appendChild(row);

        });


        attachActionButtons();
    }


    // ==========================================
    // HTML ESCAPE
    // ==========================================

    function escapeHtml(value) {

        return String(value)
            .replace(/&/g, "&amp;")
            .replace(/</g, "&lt;")
            .replace(/>/g, "&gt;")
            .replace(/"/g, "&quot;")
            .replace(/'/g, "&#039;");
    }


    // ==========================================
    // EDIT + DELETE BUTTONS
    // ==========================================

    function attachActionButtons() {

        const editButtons =
            document.querySelectorAll(".edit-btn");


        editButtons.forEach(function (button) {

            button.addEventListener(
                "click",
                function () {

                    const jobId =
                        Number(button.dataset.id);

                    startEditJob(jobId);
                }
            );
        });


        const deleteButtons =
            document.querySelectorAll(".delete-btn");


        deleteButtons.forEach(function (button) {

            button.addEventListener(
                "click",
                function () {

                    const jobId =
                        Number(button.dataset.id);

                    deleteJob(jobId);
                }
            );
        });
    }


    // ==========================================
    // START EDITING JOB
    // ==========================================

    function startEditJob(jobId) {

        const job =
            allJobs.find(function (item) {
                return item.jobId === jobId;
            });


        if (!job) {

            alert("Job not found.");
            return;
        }


        editingJobId =
            jobId;


        companySelect.value =
            job.companyId;

        jobTitle.value =
            job.jobTitle || "";

        jobLocation.value =
            job.jobLocation || "";

        salaryPackage.value =
            job.salaryPackage ?? "";

        minimumCgpa.value =
            job.minimumCgpa ?? "";

        eligibleDepartment.value =
            job.eligibleDepartment || "";

        graduationYear.value =
            job.graduationYear ?? "";

        applicationDeadline.value =
            job.applicationDeadline || "";

        requiredSkills.value =
            job.requiredSkills || "";

        jobDescription.value =
            job.jobDescription || "";


        const submitButton =
            jobForm.querySelector(
                'button[type="submit"]'
            );


        if (submitButton) {

            submitButton.textContent =
                "Update Job";
        }


        formMessage.textContent =
            "You are editing Job ID " +
            jobId +
            ". Update the details and submit the form.";

        formMessage.className =
            "message info";


        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });
    }


    // ==========================================
    // RESET FORM
    // ==========================================

    function resetJobForm() {

        jobForm.reset();

        editingJobId = null;


        const submitButton =
            jobForm.querySelector(
                'button[type="submit"]'
            );


        if (submitButton) {

            submitButton.textContent =
                "Add Job";
        }
    }


    // ==========================================
    // ADD / UPDATE JOB
    // ==========================================

    jobForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            const companyId =
                Number(companySelect.value);

            const title =
                jobTitle.value.trim();

            const location =
                jobLocation.value.trim();

            const salary =
                Number(salaryPackage.value);

            const cgpa =
                Number(minimumCgpa.value);

            const department =
                eligibleDepartment.value.trim();

            const year =
                Number(graduationYear.value);

            const deadline =
                applicationDeadline.value;

            const skills =
                requiredSkills.value.trim();

            const description =
                jobDescription.value.trim();


            // ==================================
            // VALIDATION
            // ==================================

            if (!companyId) {

                alert("Please select a company.");
                return;
            }


            if (!title) {

                alert("Please enter the job title.");
                return;
            }


            if (!location) {

                alert("Please enter the job location.");
                return;
            }


            if (
                Number.isNaN(salary) ||
                salary < 0
            ) {

                alert("Please enter a valid salary package.");
                return;
            }


            if (
                Number.isNaN(cgpa) ||
                cgpa < 0 ||
                cgpa > 10
            ) {

                alert("Please enter a valid CGPA between 0 and 10.");
                return;
            }


            if (!department) {

                alert("Please enter the eligible department.");
                return;
            }


            if (
                Number.isNaN(year) ||
                year < 2000
            ) {

                alert("Please enter a valid graduation year.");
                return;
            }


            if (!deadline) {

                alert("Please select an application deadline.");
                return;
            }


            if (!skills) {

                alert("Please enter required skills.");
                return;
            }


            if (!description) {

                alert("Please enter the job description.");
                return;
            }


            const jobData = {

                companyId: companyId,

                jobTitle: title,

                jobLocation: location,

                salaryPackage: salary,

                minimumCgpa: cgpa,

                eligibleDepartment: department,

                graduationYear: year,

                applicationDeadline: deadline,

                requiredSkills: skills,

                jobDescription: description
            };


            try {

                let response;


                // ==================================
                // UPDATE EXISTING JOB
                // ==================================

                if (editingJobId !== null) {

                    response =
                        await fetch(
                            API_URL +
                            "?jobId=" +
                            editingJobId,
                            {
                                method: "PUT",

                                headers: {
                                    "Content-Type":
                                        "application/json"
                                },

                                body:
                                    JSON.stringify(jobData)
                            }
                        );

                }

                // ==================================
                // ADD NEW JOB
                // ==================================

                else {

                    response =
                        await fetch(
                            API_URL,
                            {
                                method: "POST",

                                headers: {
                                    "Content-Type":
                                        "application/json"
                                },

                                body:
                                    JSON.stringify(jobData)
                            }
                        );
                }


                const result =
                    await response.text();


                if (!response.ok) {

                    throw new Error(result);
                }


                formMessage.textContent =
                    result;

                formMessage.className =
                    "message success";


                resetJobForm();

                await loadJobs();


            } catch (error) {

                console.error(error);

                formMessage.textContent =
                    "Operation failed: " +
                    error.message;

                formMessage.className =
                    "message error";
            }

        }
    );


    // ==========================================
    // DELETE JOB
    // ==========================================

    async function deleteJob(jobId) {

        const job =
            allJobs.find(function (item) {
                return item.jobId === jobId;
            });


        const jobTitleText =
            job
                ? job.jobTitle
                : "this job";


        const confirmed =
            confirm(
                "Are you sure you want to delete \"" +
                jobTitleText +
                "\"?"
            );


        if (!confirmed) {
            return;
        }


        try {

            const response =
                await fetch(
                    API_URL +
                    "?jobId=" +
                    jobId,
                    {
                        method: "DELETE"
                    }
                );


            const result =
                await response.text();


            if (!response.ok) {

                throw new Error(result);
            }


            jobsMessage.textContent =
                result;

            jobsMessage.className =
                "message success";


            await loadJobs();


        } catch (error) {

            console.error(error);

            jobsMessage.textContent =
                "Delete failed: " +
                error.message;

            jobsMessage.className =
                "message error";
        }
    }


    // ==========================================
    // SEARCH JOBS
    // ==========================================

    searchInput.addEventListener(
        "input",
        function () {

            const searchTerm =
                searchInput.value
                    .trim()
                    .toLowerCase();


            if (searchTerm === "") {

                displayJobs(allJobs);
                return;
            }


            const filteredJobs =
                allJobs.filter(function (job) {

                    const company =
                        companies.find(
                            function (item) {
                                return item.companyId === job.companyId;
                            }
                        );


                    const companyName =
                        company
                            ? company.companyName
                            : "";


                    return (

                        String(job.jobId)
                            .toLowerCase()
                            .includes(searchTerm)

                        ||

                        companyName
                            .toLowerCase()
                            .includes(searchTerm)

                        ||

                        String(job.jobTitle || "")
                            .toLowerCase()
                            .includes(searchTerm)

                        ||

                        String(job.jobLocation || "")
                            .toLowerCase()
                            .includes(searchTerm)

                        ||

                        String(job.eligibleDepartment || "")
                            .toLowerCase()
                            .includes(searchTerm)

                        ||

                        String(job.requiredSkills || "")
                            .toLowerCase()
                            .includes(searchTerm)
                    );

                });


            displayJobs(filteredJobs);
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
    // INITIAL LOAD
    // ==========================================

    async function initializePage() {

        await loadCompanies();

        await loadJobs();
    }


    initializePage();

});