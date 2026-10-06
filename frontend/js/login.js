document.addEventListener("DOMContentLoaded", function () {

    const loginForm = document.querySelector("form");

    loginForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const email =
            document.getElementById("email").value.trim();

        const password =
            document.getElementById("password").value.trim();

        const role =
            document.getElementById("role").value;


        // ==============================
        // VALIDATION
        // ==============================

        if (email === "" || password === "" || role === "") {

            alert("Please fill in all fields.");
            return;
        }


        // ==============================
        // STUDENT LOGIN
        // ==============================

        if (role === "student") {

            try {

                const response = await fetch(
                    "http://localhost:8080/login",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify({
                            email: email,
                            password: password
                        })
                    }
                );


                const result =
                    await response.text();


                if (response.ok) {

                    localStorage.setItem(
                        "studentEmail",
                        email
                    );

                    alert(result);

                    window.location.href =
                        "student-dashboard.html";

                } else {

                    alert(
                        "Login failed: " + result
                    );
                }


            } catch (error) {

                console.error(error);

                alert(
                    "Could not connect to the Java backend. " +
                    "Please make sure the server is running."
                );
            }
        }


        // ==============================
        // ADMIN LOGIN
        // ==============================

        else if (role === "admin") {

            try {

                const response = await fetch(
                    "http://localhost:8080/admin-login",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type": "application/json"
                        },

                        body: JSON.stringify({
                            email: email,
                            password: password
                        })
                    }
                );


                const result =
                    await response.text();


                if (response.ok) {

                    localStorage.setItem(
                        "adminEmail",
                        email
                    );

                    alert(result);

                    window.location.href =
                        "admin-dashboard.html";

                } else {

                    alert(
                        "Admin login failed: " + result
                    );
                }


            } catch (error) {

                console.error(error);

                alert(
                    "Could not connect to the Java backend. " +
                    "Please make sure the server is running."
                );
            }
        }

    });

});