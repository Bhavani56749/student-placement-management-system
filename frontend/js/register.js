document.addEventListener("DOMContentLoaded", function () {

    const registrationForm =
        document.getElementById("registrationForm");

    registrationForm.addEventListener("submit", async function (event) {

        event.preventDefault();

        const fullName =
            document.getElementById("fullName").value.trim();

        const email =
            document.getElementById("email").value.trim();

        const phone =
            document.getElementById("phone").value.trim();

        const dateOfBirth =
            document.getElementById("dateOfBirth").value;

        const gender =
            document.getElementById("gender").value;

        const department =
            document.getElementById("department").value;

        const graduationYear =
            document.getElementById("graduationYear").value;

        const cgpa =
            document.getElementById("cgpa").value;

        const address =
            document.getElementById("address").value.trim();

        const password =
            document.getElementById("password").value;


        // ==============================
        // VALIDATION
        // ==============================

        if (
            fullName === "" ||
            email === "" ||
            phone === "" ||
            dateOfBirth === "" ||
            gender === "" ||
            department === "" ||
            graduationYear === "" ||
            cgpa === "" ||
            address === "" ||
            password === ""
        ) {
            alert("Please fill in all fields.");
            return;
        }


        if (phone.length !== 10 || isNaN(phone)) {

            alert(
                "Please enter a valid 10-digit phone number."
            );

            return;
        }


        if (cgpa < 0 || cgpa > 10) {

            alert(
                "CGPA must be between 0 and 10."
            );

            return;
        }


        if (password.length < 6) {

            alert(
                "Password must contain at least 6 characters."
            );

            return;
        }


        // ==============================
        // CREATE REGISTRATION DATA
        // ==============================

        const studentData = {

            fullName: fullName,

            email: email,

            phone: phone,

            dateOfBirth: dateOfBirth,

            gender: gender,

            department: department,

            graduationYear: graduationYear,

            cgpa: parseFloat(cgpa),

            address: address,

            password: password
        };


        // ==============================
        // SEND DATA TO JAVA BACKEND
        // ==============================

        try {

            const response = await fetch(
                "http://localhost:8080/register",
                {
                    method: "POST",

                    headers: {
                        "Content-Type":
                            "application/json"
                    },

                    body: JSON.stringify(studentData)
                }
            );


            const result =
                await response.text();


            if (response.ok) {

                alert(result);

                registrationForm.reset();

            } else {

                alert(
                    "Registration failed: " + result
                );
            }

        } catch (error) {

            console.error(error);

            alert(
                "Could not connect to the Java backend. " +
                "Please make sure the server is running."
            );
        }

    });

});