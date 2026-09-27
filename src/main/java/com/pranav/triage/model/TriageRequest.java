package com.pranav.triage.model;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TriageRequest {

    @Min(0)
    @Max(130)
    private Integer age;

    @DecimalMin("90.0")
    @DecimalMax("110.0")
    private Double temperatureF;

    @Min(40)
    @Max(220)
    private Integer heartRate;

    @Min(50)
    @Max(100)
    private Integer oxygenSaturation;

    @Min(0)
    @Max(10)
    private Integer painScore;

    @NotNull
    private Boolean chestPain;

    @NotNull
    private Boolean shortnessOfBreath;

    @NotNull
    private Boolean severeWeakness;

    @NotNull
    private Boolean difficultySpeaking;

    @NotNull
    private Boolean confusion;

    @NotNull
    private Boolean diabetes;

    @NotNull
    private Boolean asthmaOrLungDisease;

    @NotNull
    private Boolean heartDisease;

    @NotNull
    private Boolean immunocompromised;

    @NotNull
    private SymptomSeverity symptomSeverity;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getTemperatureF() {
        return temperatureF;
    }

    public void setTemperatureF(Double temperatureF) {
        this.temperatureF = temperatureF;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Integer getOxygenSaturation() {
        return oxygenSaturation;
    }

    public void setOxygenSaturation(Integer oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    public Integer getPainScore() {
        return painScore;
    }

    public void setPainScore(Integer painScore) {
        this.painScore = painScore;
    }

    public Boolean getChestPain() {
        return chestPain;
    }

    public void setChestPain(Boolean chestPain) {
        this.chestPain = chestPain;
    }

    public Boolean getShortnessOfBreath() {
        return shortnessOfBreath;
    }

    public void setShortnessOfBreath(Boolean shortnessOfBreath) {
        this.shortnessOfBreath = shortnessOfBreath;
    }

    public Boolean getSevereWeakness() {
        return severeWeakness;
    }

    public void setSevereWeakness(Boolean severeWeakness) {
        this.severeWeakness = severeWeakness;
    }

    public Boolean getDifficultySpeaking() {
        return difficultySpeaking;
    }

    public void setDifficultySpeaking(Boolean difficultySpeaking) {
        this.difficultySpeaking = difficultySpeaking;
    }

    public Boolean getConfusion() {
        return confusion;
    }

    public void setConfusion(Boolean confusion) {
        this.confusion = confusion;
    }

    public Boolean getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(Boolean diabetes) {
        this.diabetes = diabetes;
    }

    public Boolean getAsthmaOrLungDisease() {
        return asthmaOrLungDisease;
    }

    public void setAsthmaOrLungDisease(Boolean asthmaOrLungDisease) {
        this.asthmaOrLungDisease = asthmaOrLungDisease;
    }

    public Boolean getHeartDisease() {
        return heartDisease;
    }

    public void setHeartDisease(Boolean heartDisease) {
        this.heartDisease = heartDisease;
    }

    public Boolean getImmunocompromised() {
        return immunocompromised;
    }

    public void setImmunocompromised(Boolean immunocompromised) {
        this.immunocompromised = immunocompromised;
    }

    public SymptomSeverity getSymptomSeverity() {
        return symptomSeverity;
    }

    public void setSymptomSeverity(SymptomSeverity symptomSeverity) {
        this.symptomSeverity = symptomSeverity;
    }

    public boolean hasNoPositiveSymptoms() {
        return Boolean.FALSE.equals(chestPain)
                && Boolean.FALSE.equals(shortnessOfBreath)
                && Boolean.FALSE.equals(severeWeakness)
                && Boolean.FALSE.equals(difficultySpeaking)
                && Boolean.FALSE.equals(confusion)
                && (temperatureF == null || temperatureF < 100.4)
                && (painScore == null || painScore == 0);
    }

    public boolean hasHighFever() {
        return temperatureF != null && temperatureF >= 103.0;
    }
}
