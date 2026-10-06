const API_BASE = "http://localhost:8080/api";

async function loadHistory() {

    const historyBody = document.getElementById("historyBody");
    const message = document.getElementById("message");

    try {

        const response = await fetch(`${API_BASE}/history`);

        if (!response.ok) {
            throw new Error("Failed to fetch history");
        }

        const history = await response.json();

        console.log("History:", history);

        historyBody.innerHTML = "";

        if (history.length === 0) {

            message.innerText = "No prediction history found.";
            return;
        }

        history.forEach(item => {

            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${item.id}</td>
                <td>${item.age}</td>
                <td>${item.glucose}</td>
                <td>${item.bmi}</td>
                <td>${item.result}</td>
            `;

            historyBody.appendChild(row);
        });

    } catch (error) {

        console.error("History error:", error);

        message.innerText =
            "Unable to load prediction history.";
    }
}


function goBack() {
    window.location.href = "dashboard.html";
}


// Load history when page opens
loadHistory();
