package com.pranav.triage.service;

import com.pranav.triage.model.SymptomSeverity;
import com.pranav.triage.model.TriageRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RiskScorer {

    public ScoreDetails score(TriageRequest request) {
        int score = 0;
        List<String> reasons = new ArrayList<>();

        if (Boolean.TRUE.equals(request.getChestPain())) {
            score += 4;
            reasons.add("Chest pain adds +4.");
        }

        if (Boolean.TRUE.equals(request.getShortnessOfBreath())) {
            score += 4;
            reasons.add("Shortness of breath adds +4.");
        }

        if (Boolean.TRUE.equals(request.getConfusion())) {
            score += 5;
            reasons.add("Confusion adds +5.");
        }

        if (request.hasHighFever()) {
            score += 3;
            reasons.add("High fever adds +3.");
        }

        if (request.getTemperatureF() != null && request.getTemperatureF() >= 100.4 && request.getTemperatureF() < 103.0) {
            score += 1;
            reasons.add("Fever adds +1.");
        }

        if (request.getPainScore() != null) {
            if (request.getPainScore() >= 8) {
                score += 3;
                reasons.add("Pain score 8-10 adds +3.");
            } else if (request.getPainScore() >= 5) {
                score += 2;
                reasons.add("Pain score 5-7 adds +2.");
            } else if (request.getPainScore() >= 1) {
                score += 1;
                reasons.add("Pain score 1-4 adds +1.");
            }
        }

        if (request.getOxygenSaturation() != null) {
            if (request.getOxygenSaturation() <= 92) {
                score += 4;
                reasons.add("Low oxygen saturation adds +4.");
            } else if (request.getOxygenSaturation() <= 95) {
                score += 2;
                reasons.add("Borderline oxygen saturation adds +2.");
            }
        }

        if (request.getHeartRate() != null && request.getHeartRate() >= 120) {
            score += 2;
            reasons.add("Heart rate 120 or above adds +2.");
        }

        if (request.getAge() != null && request.getAge() >= 65) {
            score += 1;
            reasons.add("Age 65 or older adds +1.");
        }

        if (Boolean.TRUE.equals(request.getHeartDisease())) {
            score += 2;
            reasons.add("Heart disease history adds +2.");
        }

        if (Boolean.TRUE.equals(request.getAsthmaOrLungDisease())) {
            score += 1;
            reasons.add("Asthma or lung disease history adds +1.");
        }

        if (Boolean.TRUE.equals(request.getDiabetes())) {
            score += 1;
            reasons.add("Diabetes adds +1.");
        }

        if (Boolean.TRUE.equals(request.getImmunocompromised())) {
            score += 2;
            reasons.add("Immunocompromised status adds +2.");
        }

        if (request.getSymptomSeverity() != null) {
            if (request.getSymptomSeverity() == SymptomSeverity.SEVERE) {
                score += 3;
                reasons.add("Severe overall symptom severity adds +3.");
            } else if (request.getSymptomSeverity() == SymptomSeverity.MODERATE) {
                score += 2;
                reasons.add("Moderate overall symptom severity adds +2.");
            } else if (request.getSymptomSeverity() == SymptomSeverity.MILD) {
                score += 1;
                reasons.add("Mild overall symptom severity adds +1.");
            }
        }

        return new ScoreDetails(score, reasons);
    }

    public record ScoreDetails(int score, List<String> reasons) {}
}
