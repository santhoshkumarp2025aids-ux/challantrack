package com.example.challantrack.service;

import com.example.challantrack.entity.Challan;
import com.example.challantrack.entity.PaymentRecord;
import com.example.challantrack.repository.ChallanRepository;
import com.example.challantrack.repository.PaymentRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentRecordService {

    private final PaymentRecordRepository paymentRecordRepository;
    private final ChallanRepository challanRepository;

    public PaymentRecordService(
            PaymentRecordRepository paymentRecordRepository,
            ChallanRepository challanRepository) {

        this.paymentRecordRepository = paymentRecordRepository;
        this.challanRepository = challanRepository;
    }

    // Record payment
    public PaymentRecord recordPayment(PaymentRecord paymentRecord) {

        Long challanId = paymentRecord.getChallan().getId();

        Challan challan = challanRepository.findById(challanId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Challan not found with ID: " + challanId
                        )
                );

        paymentRecord.setChallan(challan);

        // Update challan payment details
        challan.setPaymentStatus("PAID");

        if (paymentRecord.getPaymentDate() == null) {
            paymentRecord.setPaymentDate(LocalDate.now());
        }

        challan.setPaymentDate(
                paymentRecord.getPaymentDate()
        );

        challanRepository.save(challan);

        return paymentRecordRepository.save(paymentRecord);
    }

    // Get all payment records
    public List<PaymentRecord> getAllPaymentRecords() {
        return paymentRecordRepository.findAll();
    }

    // Get payment record by ID
    public PaymentRecord getPaymentRecordById(Long id) {

        return paymentRecordRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment record not found with ID: " + id
                        )
                );
    }
}