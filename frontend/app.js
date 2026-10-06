
const API_BASE = "http://localhost:8080/api";

async function login() {
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    // Check empty fields
    if (!email || !password) {
        alert("Please enter email and password");
        return;
    }

    console.log("Email:", email);
    console.log("Password:", password);

    try {
        const response = await fetch(`${API_BASE}/users/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        // Read response safely
        const text = await response.text();

        let data;

        try {
            data = JSON.parse(text);
        } catch {
            data = text;
        }

        console.log("Login response:", data);
        console.log("Status:", response.status);

        // Login successful
        if (response.ok) {
            alert("Login successful!");

            // Save logged-in user's email
            localStorage.setItem("userEmail", email);

            // Go to dashboard
            window.location.href = "dashboard.html";
        }

        // Login failed
        else {
            if (typeof data === "string") {
                alert(data || "Invalid email or password");
            } else {
                alert(data.message || "Invalid email or password");
            }
        }

    } catch (error) {
        console.error("Backend connection error:", error);

        alert(
            "Backend connection failed.\n\n" +
            "Please make sure Spring Boot is running on port 8080."
        );
    }
}