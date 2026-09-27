package com.pranav.triage.controller;

import com.pranav.triage.model.TriageRequest;
import com.pranav.triage.model.TriageResult;
import com.pranav.triage.service.TriageService;

import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TriageController {

    private final TriageService triageService;

    public TriageController(TriageService triageService) {
        this.triageService = triageService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok");
    }

    @PostMapping("/triage/evaluate")
    public ResponseEntity<TriageResult> evaluate(@Valid @RequestBody TriageRequest request) {
        return ResponseEntity.ok(triageService.evaluate(request));
    }
}
