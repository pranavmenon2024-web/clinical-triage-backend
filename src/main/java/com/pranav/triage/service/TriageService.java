package com.pranav.triage.service;

import com.pranav.triage.model.TriageLevel;
import com.pranav.triage.model.TriageRequest;
import com.pranav.triage.model.TriageResult;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TriageService {

    private final RedFlagEvaluator redFlagEvaluator;
    private final RiskScorer riskScorer;
    private final RecommendationService recommendationService;

    public TriageService(RedFlagEvaluator redFlagEvaluator,
                         RiskScorer riskScorer,
                         RecommendationService recommendationService) {
        this.redFlagEvaluator = redFlagEvaluator;
        this.riskScorer = riskScorer;
        this.recommendationService = recommendationService;
    }

    public TriageResult evaluate(TriageRequest request) {
        TriageResult result = new TriageResult();
        result.setTimestamp(Instant.now());
        result.setDisclaimer("This tool is for educational use only and is not a medical diagnosis.");

        List<String> redFlags = redFlagEvaluator.evaluate(request);
        if (!redFlags.isEmpty()) {
            result.setLevel(TriageLevel.EMERGENCY);
            result.setScore(999);
            result.setEmergencyOverrideTriggered(true);
            result.setMatchedRules(new ArrayList<>(List.of("RED_FLAG_OVERRIDE")));
            result.setReasons(redFlags);
            result.setExplanation("Emergency recommendation was triggered because one or more red-flag conditions were detected.");
            result.setNextAction(recommendationService.nextAction(TriageLevel.EMERGENCY));
            return result;
        }

        RiskScorer.ScoreDetails scoreDetails = riskScorer.score(request);
        TriageLevel level = recommendationService.determineLevel(scoreDetails.score(), request.hasNoPositiveSymptoms());

        result.setLevel(level);
        result.setScore(scoreDetails.score());
        result.setEmergencyOverrideTriggered(false);
        result.setMatchedRules(buildRuleSummary(level));
        result.setReasons(scoreDetails.reasons());
        result.setExplanation(buildExplanation(level, scoreDetails.score()));
        result.setNextAction(recommendationService.nextAction(level));

        return result;
    }

    private List<String> buildRuleSummary(TriageLevel level) {
        List<String> rules = new ArrayList<>();
        rules.add("WEIGHTED_SCORING");
        rules.add("DISPOSITION_" + level.name());
        return rules;
    }

    private String buildExplanation(TriageLevel level, int score) {
        return switch (level) {
            case EMERGENCY -> "The combined symptom and risk-factor score of " + score + " meets the emergency threshold.";
            case URGENT -> "The combined symptom and risk-factor score of " + score + " supports urgent evaluation.";
            case PRIMARY_CARE -> "The current information suggests follow-up is needed, but it does not meet urgent or emergency thresholds.";
            case HOME_CARE -> "No major concerning features were identified from the submitted information.";
        };
    }
}
