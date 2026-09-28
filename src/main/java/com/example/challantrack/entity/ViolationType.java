package com.example.challantrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "violation_types")
public class ViolationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Violation name is required")
    private String violationName;

    @Positive(message = "Base fine must be greater than 0")
    private double baseFine;

    // Default constructor
    public ViolationType() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getViolationName() {
        return violationName;
    }

    public void setViolationName(String violationName) {
        this.violationName = violationName;
    }

    public double getBaseFine() {
        return baseFine;
    }

    public void setBaseFine(double baseFine) {
        this.baseFine = baseFine;
    }
}