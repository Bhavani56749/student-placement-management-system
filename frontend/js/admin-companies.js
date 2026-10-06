document.addEventListener("DOMContentLoaded", function () {

    // ==========================================
    // GET PAGE ELEMENTS
    // ==========================================

    const companyForm =
        document.getElementById("companyForm");

    const companyNameInput =
        document.getElementById("companyName");

    const industryInput =
        document.getElementById("industry");

    const locationInput =
        document.getElementById("location");

    const websiteInput =
        document.getElementById("website");

    const contactPersonInput =
        document.getElementById("contactPerson");

    const contactEmailInput =
        document.getElementById("contactEmail");

    const formMessage =
        document.getElementById("formMessage");

    const companiesTableBody =
        document.getElementById("companiesTableBody");

    const companiesMessage =
        document.getElementById("companiesMessage");

    const totalCompanies =
        document.getElementById("totalCompanies");

    const searchInput =
        document.getElementById("searchInput");

    const backButton =
        document.getElementById("backButton");

    const logoutButton =
        document.getElementById("logoutButton");


    // ==========================================
    // API URL
    // ==========================================

    const API_URL =
        "http://localhost:8080/companies";


    // ==========================================
    // STORE COMPANIES
    // ==========================================

    let companies = [];


    // ==========================================
    // LOAD COMPANIES
    // ==========================================

    async function loadCompanies() {

        try {

            companiesMessage.style.display =
                "block";

            companiesMessage.textContent =
                "Loading companies...";


            const response =
                await fetch(API_URL);


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


            totalCompanies.textContent =
                companies.length;


            displayCompanies(companies);


        } catch (error) {

            console.error(
                "Company loading error:",
                error
            );


            companies = [];


            totalCompanies.textContent =
                "-";


            companiesTableBody.innerHTML =
                "";


            companiesMessage.style.display =
                "block";


            companiesMessage.innerHTML = `
                Could not load companies.

                <br><br>

                Please make sure the backend
                server is running.
            `;

        }

    }


    // ==========================================
    // DISPLAY COMPANIES
    // ==========================================

    function displayCompanies(companyList) {

        companiesTableBody.innerHTML =
            "";


        if (
            !companyList ||
            companyList.length === 0
        ) {

            companiesMessage.style.display =
                "block";

            companiesMessage.textContent =
                "No companies found.";

            return;

        }


        companiesMessage.style.display =
            "none";


        companyList.forEach(
            function (company) {

                const row =
                    document.createElement("tr");


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
                // TABLE ROW
                // ==================================

                row.innerHTML = `

                    <td>
                        ${company.companyId}
                    </td>

                    <td>
                        ${company.companyName}
                    </td>

                    <td>
                        ${company.industry}
                    </td>

                    <td>
                        ${company.location}
                    </td>

                    <td>
                        ${websiteHTML}
                    </td>

                    <td>
                        ${contactPerson}
                    </td>

                    <td>
                        ${contactEmail}
                    </td>

                    <td>

                        <button
                            class="action-button edit-button"
                            data-id="${company.companyId}">

                            Edit

                        </button>


                        <button
                            class="action-button delete-button"
                            data-id="${company.companyId}">

                            Delete

                        </button>

                    </td>

                `;


                companiesTableBody.appendChild(
                    row
                );

            }
        );


        // ==========================================
        // ADD EDIT BUTTON EVENTS
        // ==========================================

        const editButtons =
            document.querySelectorAll(
                ".edit-button"
            );


        editButtons.forEach(
            function (button) {

                button.addEventListener(
                    "click",
                    function () {

                        const companyId =
                            parseInt(
                                button.dataset.id
                            );


                        editCompany(
                            companyId
                        );

                    }
                );

            }
        );


        // ==========================================
        // ADD DELETE BUTTON EVENTS
        // ==========================================

        const deleteButtons =
            document.querySelectorAll(
                ".delete-button"
            );


        deleteButtons.forEach(
            function (button) {

                button.addEventListener(
                    "click",
                    function () {

                        const companyId =
                            parseInt(
                                button.dataset.id
                            );


                        deleteCompany(
                            companyId
                        );

                    }
                );

            }
        );

    }


    // ==========================================
    // ADD COMPANY
    // ==========================================

    companyForm.addEventListener(
        "submit",
        async function (event) {

            event.preventDefault();


            formMessage.textContent =
                "Adding company...";


            formMessage.style.color =
                "#2563eb";


            const companyData = {

                companyName:
                    companyNameInput.value.trim(),

                industry:
                    industryInput.value.trim(),

                location:
                    locationInput.value.trim(),

                website:
                    websiteInput.value.trim(),

                contactPerson:
                    contactPersonInput.value.trim(),

                contactEmail:
                    contactEmailInput.value.trim()

            };


            try {

                const response =
                    await fetch(
                        API_URL,
                        {
                            method: "POST",

                            headers: {
                                "Content-Type":
                                    "application/json"
                            },

                            body:
                                JSON.stringify(
                                    companyData
                                )
                        }
                    );


                const result =
                    await response.text();


                console.log(
                    "Add company response:",
                    result
                );


                if (!response.ok) {

                    throw new Error(
                        result ||
                        "Company could not be added."
                    );

                }


                formMessage.textContent =
                    "Company added successfully.";


                formMessage.style.color =
                    "#15803d";


                companyForm.reset();


                await loadCompanies();


            } catch (error) {

                console.error(
                    "Add company error:",
                    error
                );


                formMessage.textContent =
                    "Could not add company: "
                    + error.message;


                formMessage.style.color =
                    "#dc2626";

            }

        }
    );


    // ==========================================
    // EDIT COMPANY
    // ==========================================

    async function editCompany(companyId) {

        const company =
            companies.find(
                function (item) {

                    return (
                        item.companyId ===
                        companyId
                    );

                }
            );


        if (!company) {

            alert(
                "Company could not be found."
            );

            return;
        }


        // ======================================
        // GET UPDATED VALUES
        // ======================================

        const companyName =
            prompt(
                "Company Name:",
                company.companyName
            );


        if (companyName === null) {
            return;
        }


        const industry =
            prompt(
                "Industry:",
                company.industry
            );


        if (industry === null) {
            return;
        }


        const location =
            prompt(
                "Location:",
                company.location
            );


        if (location === null) {
            return;
        }


        const website =
            prompt(
                "Website:",
                company.website === "null"
                    ? ""
                    : company.website
            );


        if (website === null) {
            return;
        }


        const contactPerson =
            prompt(
                "Contact Person:",
                company.contactPerson === "null"
                    ? ""
                    : company.contactPerson
            );


        if (contactPerson === null) {
            return;
        }


        const contactEmail =
            prompt(
                "Contact Email:",
                company.contactEmail === "null"
                    ? ""
                    : company.contactEmail
            );


        if (contactEmail === null) {
            return;
        }


        // ======================================
        // CREATE UPDATED DATA
        // ======================================

        const updatedCompany = {

            companyName:
                companyName.trim(),

            industry:
                industry.trim(),

            location:
                location.trim(),

            website:
                website.trim(),

            contactPerson:
                contactPerson.trim(),

            contactEmail:
                contactEmail.trim()

        };


        try {

            const response =
                await fetch(
                    API_URL
                    + "?companyId="
                    + companyId,
                    {
                        method: "PUT",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body:
                            JSON.stringify(
                                updatedCompany
                            )
                    }
                );


            const result =
                await response.text();


            console.log(
                "Update company response:",
                result
            );


            if (!response.ok) {

                throw new Error(
                    result ||
                    "Company could not be updated."
                );

            }


            alert(
                "Company updated successfully."
            );


            await loadCompanies();


        } catch (error) {

            console.error(
                "Update company error:",
                error
            );


            alert(
                "Could not update company.\n\n"
                + error.message
            );

        }

    }


    // ==========================================
    // DELETE COMPANY
    // ==========================================

    async function deleteCompany(companyId) {

        const company =
            companies.find(
                function (item) {

                    return (
                        item.companyId ===
                        companyId
                    );

                }
            );


        if (!company) {

            alert(
                "Company could not be found."
            );

            return;
        }


        const confirmed =
            confirm(
                "Are you sure you want to delete "
                + company.companyName
                + "?"
            );


        if (!confirmed) {

            return;
        }


        try {

            const response =
                await fetch(
                    API_URL
                    + "?companyId="
                    + companyId,
                    {
                        method: "DELETE"
                    }
                );


            const result =
                await response.text();


            console.log(
                "Delete company response:",
                result
            );


            if (!response.ok) {

                throw new Error(
                    result ||
                    "Company could not be deleted."
                );

            }


            alert(
                "Company deleted successfully."
            );


            await loadCompanies();


        } catch (error) {

            console.error(
                "Delete company error:",
                error
            );


            alert(
                "Could not delete company.\n\n"
                + error.message
            );

        }

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

                            String(
                                company.companyId
                            )
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                            ||

                            String(
                                company.companyName
                            )
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                            ||

                            String(
                                company.industry
                            )
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                            ||

                            String(
                                company.location
                            )
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                            ||

                            String(
                                company.contactPerson
                            )
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                            ||

                            String(
                                company.contactEmail
                            )
                                .toLowerCase()
                                .includes(
                                    searchText
                                )

                        );

                    }
                );


            displayCompanies(
                filteredCompanies
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

    loadCompanies();

});