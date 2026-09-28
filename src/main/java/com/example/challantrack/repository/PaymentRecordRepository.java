package com.example.challantrack.repository;

import com.example.challantrack.entity.PaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRecordRepository
        extends JpaRepository<PaymentRecord, Long> {
}