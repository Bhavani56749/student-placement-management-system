document.addEventListener("DOMContentLoaded", async function () {

    const companiesContainer =
        document.getElementById("companiesContainer");

    const searchInput =
        document.getElementById("searchInput");

    const backButton =
        document.getElementById("backButton");

    const logoutButton =
        document.getElementById("logoutButton");


    // ==========================================
    // STORE ALL COMPANIES
    // ==========================================

    let companies = [];


    // ==========================================
    // LOAD COMPANIES
    // ==========================================

    async function loadCompanies() {

        try {

            companiesContainer.innerHTML = `
                <div class="message">
                    Loading companies...
                </div>
            `;


            const response =
                await fetch(
                    "http://localhost:8080/companies"
                );


            if (!response.ok) {

                throw new Error(
                    "Companies could not be loaded."
                );

            }


            companies =
                await response.json();


            console.log(
                "Companies:",
                companies
            );


            if (
                !companies ||
                companies.length === 0
            ) {

                companiesContainer.innerHTML = `
                    <div class="message">
                        No companies available.
                    </div>
                `;

                return;
            }


            // Display companies
            displayCompanies(companies);


        } catch (error) {

            console.error(
                "Companies loading error:",
                error
            );


            companiesContainer.innerHTML = `
                <div class="message">
                    Could not load companies.
                    <br><br>
                    Please make sure the backend
                    server is running.
                </div>
            `;

        }

    }


    // ==========================================
    // DISPLAY COMPANIES
    // ==========================================

    function displayCompanies(companyList) {

        companiesContainer.innerHTML = "";


        if (
            !companyList ||
            companyList.length === 0
        ) {

            companiesContainer.innerHTML = `
                <div class="message">
                    No companies found.
                </div>
            `;

            return;
        }


        companyList.forEach(
            function (company) {


                // ==================================
                // CREATE COMPANY CARD
                // ==================================

                const card =
                    document.createElement("div");


                card.className =
                    "company-card";


                // ==================================
                // WEBSITE
                // ==================================

                let websiteHTML =
                    "Not available";


                if (
                    company.website &&
                    company.website !== "null"
                ) {

                    websiteHTML = `
                        <a
                            href="${company.website}"
                            target="_blank"
                            class="website-link">
                            Visit Website
                        </a>
                    `;

                }


                // ==================================
                // CONTACT PERSON
                // ==================================

                const contactPerson =
                    company.contactPerson &&
                    company.contactPerson !== "null"
                        ? company.contactPerson
                        : "Not available";


                // ==================================
                // CONTACT EMAIL
                // ==================================

                const contactEmail =
                    company.contactEmail &&
                    company.contactEmail !== "null"
                        ? company.contactEmail
                        : "Not available";


                // ==================================
                // CREATE HTML
                // ==================================

                card.innerHTML = `

                    <div class="company-header">

                        <div class="company-name">
                            ${company.companyName}
                        </div>

                        <span class="company-industry">
                            ${company.industry}
                        </span>

                    </div>


                    <div class="company-details">


                        <div class="detail-row">

                            <span class="detail-label">
                                Company ID
                            </span>

                            <span class="detail-value">
                                ${company.companyId}
                            </span>

                        </div>


                        <div class="detail-row">

                            <span class="detail-label">
                                Location
                            </span>

                            <span class="detail-value">
                                ${company.location}
                            </span>

                        </div>


                        <div class="detail-row">

                            <span class="detail-label">
                                Website
                            </span>

                            <span class="detail-value">
                                ${websiteHTML}
                            </span>

                        </div>


                        <div class="detail-row">

                            <span class="detail-label">
                                Contact Person
                            </span>

                            <span class="detail-value">
                                ${contactPerson}
                            </span>

                        </div>


                        <div class="detail-row">

                            <span class="detail-label">
                                Contact Email
                            </span>

                            <span class="detail-value">
                                ${contactEmail}
                            </span>

                        </div>


                    </div>

                `;


                companiesContainer.appendChild(
                    card
                );

            }
        );

    }


    // ==========================================
    // SEARCH COMPANIES
    // ==========================================

    searchInput.addEventListener(
        "input",
        function () {

            const searchText =
                searchInput.value
                    .toLowerCase()
                    .trim();


            const filteredCompanies =
                companies.filter(
                    function (company) {

                        return (

                            company.companyName
                                .toLowerCase()
                                .includes(searchText)

                            ||

                            company.industry
                                .toLowerCase()
                                .includes(searchText)

                            ||

                            company.location
                                .toLowerCase()
                                .includes(searchText)

                        );

                    }
                );


            displayCompanies(
                filteredCompanies
            );

        }
    );


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


    // ==========================================
    // START
    // ==========================================

    loadCompanies();

});