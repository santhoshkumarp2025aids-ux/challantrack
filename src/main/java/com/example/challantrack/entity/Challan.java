package com.example.challantrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "challans")
public class Challan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Vehicle against which the challan is issued
    @NotNull(message = "Vehicle is required")
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    // Type of traffic violation
    @NotNull(message = "Violation type is required")
    @ManyToOne
    @JoinColumn(name = "violation_type_id")
    private ViolationType violationType;

    // Fine amount - calculated automatically by ChallanService
    private double fineAmount;

    // Date when challan is issued
    @NotNull(message = "Challan date is required")
    private LocalDate challanDate;

    // PAID or UNPAID
    private String paymentStatus;

    // Date of payment
    private LocalDate paymentDate;

    // Default constructor
    public Challan() {
    }

    // Get ID
    public Long getId() {
        return id;
    }

    // Set ID
    public void setId(Long id) {
        this.id = id;
    }

    // Get Vehicle
    public Vehicle getVehicle() {
        return vehicle;
    }

    // Set Vehicle
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    // Get Violation Type
    public ViolationType getViolationType() {
        return violationType;
    }

    // Set Violation Type
    public void setViolationType(ViolationType violationType) {
        this.violationType = violationType;
    }

    // Get Fine Amount
    public double getFineAmount() {
        return fineAmount;
    }

    // Set Fine Amount
    public void setFineAmount(double fineAmount) {
        this.fineAmount = fineAmount;
    }

    // Get Challan Date
    public LocalDate getChallanDate() {
        return challanDate;
    }

    // Set Challan Date
    public void setChallanDate(LocalDate challanDate) {
        this.challanDate = challanDate;
    }

    // Get Payment Status
    public String getPaymentStatus() {
        return paymentStatus;
    }

    // Set Payment Status
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Get Payment Date
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    // Set Payment Date
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}