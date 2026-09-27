package com.pranav.triage.service;

import com.pranav.triage.model.TriageLevel;
import org.springframework.stereotype.Component;

@Component
public class RecommendationService {

    private static final int EMERGENCY_THRESHOLD = 8;
    private static final int URGENT_THRESHOLD = 4;

    public TriageLevel determineLevel(int score, boolean noSymptoms) {
        if (score >= EMERGENCY_THRESHOLD) {
            return TriageLevel.EMERGENCY;
        }

        if (score >= URGENT_THRESHOLD) {
            return TriageLevel.URGENT;
        }

        if (noSymptoms) {
            return TriageLevel.HOME_CARE;
        }

        return TriageLevel.PRIMARY_CARE;
    }

    public String nextAction(TriageLevel level) {
        return switch (level) {
            case EMERGENCY -> "Seek emergency care immediately or call 911 now.";
            case URGENT -> "Go to urgent care or obtain same-day medical evaluation.";
            case PRIMARY_CARE -> "Arrange follow-up with a primary care clinician soon, especially if symptoms persist or worsen.";
            case HOME_CARE -> "Monitor symptoms at home and seek care if anything worsens or new red flags appear.";
        };
    }
}
