package com.example.challantrack.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
@Table(name = "payment_records")
public class PaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Challan for which payment is made
    @NotNull(message = "Challan is required")
    @OneToOne
    @JoinColumn(name = "challan_id")
    private Challan challan;

    // Date on which payment was made
    @NotNull(message = "Payment date is required")
    private LocalDate paymentDate;

    // Amount paid
    @Positive(message = "Amount paid must be greater than 0")
    private double amountPaid;

    // Default constructor
    public PaymentRecord() {
    }

    // Get ID
    public Long getId() {
        return id;
    }

    // Set ID
    public void setId(Long id) {
        this.id = id;
    }

    // Get Challan
    public Challan getChallan() {
        return challan;
    }

    // Set Challan
    public void setChallan(Challan challan) {
        this.challan = challan;
    }

    // Get Payment Date
    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    // Set Payment Date
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Get Amount Paid
    public double getAmountPaid() {
        return amountPaid;
    }

    // Set Amount Paid
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}