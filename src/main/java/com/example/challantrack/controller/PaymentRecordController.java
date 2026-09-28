package com.example.challantrack.controller;

import com.example.challantrack.entity.PaymentRecord;
import com.example.challantrack.service.PaymentRecordService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    public PaymentRecordController(
            PaymentRecordService paymentRecordService) {

        this.paymentRecordService = paymentRecordService;
    }

    // RECORD PAYMENT
    @PostMapping
    public ResponseEntity<PaymentRecord> recordPayment(
            @Valid @RequestBody PaymentRecord paymentRecord) {

        return ResponseEntity.ok(
                paymentRecordService.recordPayment(paymentRecord)
        );
    }

    // GET ALL PAYMENTS
    @GetMapping
    public ResponseEntity<List<PaymentRecord>> getAllPayments() {

        return ResponseEntity.ok(
                paymentRecordService.getAllPaymentRecords()
        );
    }

    // GET PAYMENT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentRecord> getPaymentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                paymentRecordService.getPaymentRecordById(id)
        );
    }
}