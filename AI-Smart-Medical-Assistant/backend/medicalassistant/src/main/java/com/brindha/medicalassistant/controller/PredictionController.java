
package com.brindha.medicalassistant.controller;

import com.brindha.medicalassistant.entity.PredictionHistory;
import com.brindha.medicalassistant.repository.PredictionHistoryRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/prediction")
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500"
})
public class PredictionController {

    private final PredictionHistoryRepository repository;

    public PredictionController(PredictionHistoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> predict(@RequestBody Map<String, Object> data) {

        try {
            int age = Integer.parseInt(data.get("age").toString());
            double glucose = Double.parseDouble(data.get("glucose").toString());
            double bmi = Double.parseDouble(data.get("bmi").toString());

            String result;

            if (glucose >= 126 || bmi >= 30) {
                result = "Higher Risk";
            } else if (glucose >= 100 || bmi >= 25) {
                result = "Moderate Risk";
            } else {
                result = "Lower Risk";
            }

            PredictionHistory history = new PredictionHistory();
            history.setAge(age);
            history.setGlucose(glucose);
            history.setBmi(bmi);
            history.setResult(result);

            repository.save(history);

            return ResponseEntity.ok(
                    Map.of(
                            "age", age,
                            "glucose", glucose,
                            "bmi", bmi,
                            "result", result,
                            "message", "Prediction completed and saved successfully!"
                    )
            );

        } catch (Exception e) {

            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Invalid input"));
        }
    }
}