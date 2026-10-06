const API_BASE = "http://127.0.0.1:8080/api";

async function predict() {

    const age = document.getElementById("age").value;
    const glucose = document.getElementById("glucose").value;
    const bmi = document.getElementById("bmi").value;

    if (!age || !glucose || !bmi) {
        alert("Please enter Age, Glucose and BMI");
        return;
    }

    try {

        // STEP 1: Get prediction from backend
        const predictionResponse = await fetch(`${API_BASE}/prediction`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                age: age,
                glucose: glucose,
                bmi: bmi
            })
        });

        const predictionData = await predictionResponse.json();

        console.log("Prediction response:", predictionData);

        if (!predictionResponse.ok) {
            document.getElementById("result").innerText =
                predictionData.message || "Prediction failed";
            return;
        }

        const result = predictionData.result;

        // STEP 2: Save prediction to database
        const historyResponse = await fetch(`${API_BASE}/history`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                age: Number(age),
                glucose: Number(glucose),
                bmi: Number(bmi),
                result: result
            })
        });

        const historyData = await historyResponse.json();

        console.log("History saved:", historyData);

        if (!historyResponse.ok) {
            document.getElementById("result").innerText =
                "Prediction: " + result +
                "\nBut history could not be saved.";
            return;
        }

        // STEP 3: Show result
        document.getElementById("result").innerText =
            "Prediction: " + result;

        alert("Prediction completed and saved successfully!");

    } catch (error) {

        console.error("Prediction error:", error);

        document.getElementById("result").innerText =
            "Backend connection failed";
    }
}