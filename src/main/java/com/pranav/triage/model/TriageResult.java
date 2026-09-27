package com.pranav.triage.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TriageResult {

    private TriageLevel level;
    private int score;
    private boolean emergencyOverrideTriggered;
    private List<String> matchedRules = new ArrayList<>();
    private List<String> reasons = new ArrayList<>();
    private String explanation;
    private String nextAction;
    private String disclaimer;
    private Instant timestamp;

    public TriageLevel getLevel() {
        return level;
    }

    public void setLevel(TriageLevel level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isEmergencyOverrideTriggered() {
        return emergencyOverrideTriggered;
    }

    public void setEmergencyOverrideTriggered(boolean emergencyOverrideTriggered) {
        this.emergencyOverrideTriggered = emergencyOverrideTriggered;
    }

    public List<String> getMatchedRules() {
        return matchedRules;
    }

    public void setMatchedRules(List<String> matchedRules) {
        this.matchedRules = matchedRules;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
