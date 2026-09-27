# Clinical Triage Backend

Spring Boot REST service for a rule-based symptom-triage tool. Given a
patient's vitals, symptoms, and risk factors, it recommends a level of care
(emergency, urgent care, primary care, or home care) and explains which rules
produced the result.

The decision logic started as a Java console program and was later rebuilt as
this service, with a React front end:
[clinical-triage-frontend](https://github.com/pranavmenon2024-web/clinical-triage-frontend).

**Educational prototype only. Not a medical device and not for clinical use.**

## How a request is evaluated

1. **Red-flag override** (`RedFlagEvaluator`) checks dangerous combinations
   first: chest pain with shortness of breath, stroke warning signs, high
   fever with confusion, oxygen saturation at or below 90%, and severe chest
   pain. If any match, the result is `EMERGENCY` and scoring is skipped.
2. **Weighted risk score** (`RiskScorer`) adds points for each finding (for
   example, chest pain +4, confusion +5, SpO2 at or below 92% +4) and records
   the reason for each.
3. **Disposition** (`RecommendationService`) maps the score to a care level
   (8 or more: emergency, 4 to 7: urgent) and a next step.

Inputs are validated server-side (age, temperature, oxygen saturation, pain
score ranges), and every response includes the rules that fired.

## Structure

```
controller/TriageController.java   REST endpoints
service/TriageService.java         runs the three steps above
service/RedFlagEvaluator.java      emergency override rules
service/RiskScorer.java            weighted scoring
service/RecommendationService.java score -> care level and next action
model/TriageRequest.java           validated request body
model/TriageResult.java            response body
```

## Run

Requires Java 17 and Maven.

```bash
mvn spring-boot:run
```

The service starts on `http://localhost:8080`.

## Endpoints

- `GET /api/health`
- `POST /api/triage/evaluate`

Example request:

```json
{
  "age": 68,
  "temperatureF": 102.4,
  "heartRate": 118,
  "oxygenSaturation": 94,
  "painScore": 6,
  "chestPain": true,
  "shortnessOfBreath": false,
  "severeWeakness": false,
  "difficultySpeaking": false,
  "confusion": false,
  "diabetes": true,
  "asthmaOrLungDisease": false,
  "heartDisease": true,
  "immunocompromised": false,
  "symptomSeverity": "MODERATE"
}
```

Response (abridged):

```json
{
  "level": "EMERGENCY",
  "score": 15,
  "emergencyOverrideTriggered": false,
  "matchedRules": ["WEIGHTED_SCORING", "DISPOSITION_EMERGENCY"],
  "reasons": [
    "Chest pain adds +4.",
    "Fever adds +1.",
    "Pain score 5-7 adds +2.",
    "Borderline oxygen saturation adds +2.",
    "Age 65 or older adds +1.",
    "Heart disease history adds +2.",
    "Diabetes adds +1.",
    "Moderate overall symptom severity adds +2."
  ],
  "nextAction": "Seek emergency care immediately or call 911 now."
}
```

## Known limitations

- Point weights and thresholds were set by clinical judgment, not fit to
  data. A logistic regression model trained on CDC emergency department data
  ([clinical-triage-ml](https://github.com/pranavmenon2024-web/clinical-triage-ml))
  is being developed as a data-driven replacement.
- On a red-flag override the score is set to a sentinel value (999); the
  override flag and reasons already carry that information, so the score
  should be left empty instead.
