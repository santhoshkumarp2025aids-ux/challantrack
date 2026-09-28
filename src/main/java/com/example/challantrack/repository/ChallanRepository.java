package com.example.challantrack.repository;

import com.example.challantrack.entity.Challan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChallanRepository extends JpaRepository<Challan, Long> {

    long countByVehicleIdAndViolationTypeIdAndChallanDateBetween(
            Long vehicleId,
            Long violationTypeId,
            LocalDate startDate,
            LocalDate endDate
    );

    List<Challan> findByPaymentStatusAndChallanDateBefore(
            String paymentStatus,
            LocalDate date
    );
}