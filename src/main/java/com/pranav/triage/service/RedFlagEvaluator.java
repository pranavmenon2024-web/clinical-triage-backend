package com.pranav.triage.service;

import com.pranav.triage.model.TriageRequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RedFlagEvaluator {

    public List<String> evaluate(TriageRequest request) {
        List<String> redFlags = new ArrayList<>();

        if (Boolean.TRUE.equals(request.getChestPain()) && Boolean.TRUE.equals(request.getShortnessOfBreath())) {
            redFlags.add("Chest pain with shortness of breath can indicate a medical emergency.");
        }

        if (Boolean.TRUE.equals(request.getSevereWeakness())) {
            redFlags.add("Sudden weakness on one side can be a stroke warning sign.");
        }

        if (Boolean.TRUE.equals(request.getDifficultySpeaking())) {
            redFlags.add("Difficulty speaking can be a stroke warning sign.");
        }

        if (request.hasHighFever() && Boolean.TRUE.equals(request.getConfusion())) {
            redFlags.add("High fever with confusion is a red-flag combination.");
        }

        if (request.getOxygenSaturation() != null && request.getOxygenSaturation() <= 90) {
            redFlags.add("Oxygen saturation at or below 90% is concerning.");
        }

        if (request.getPainScore() != null && request.getPainScore() >= 9 && Boolean.TRUE.equals(request.getChestPain())) {
            redFlags.add("Very severe chest pain is concerning for emergency evaluation.");
        }

        return redFlags;
    }
}
