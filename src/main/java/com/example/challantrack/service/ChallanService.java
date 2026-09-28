package com.example.challantrack.service;

import com.example.challantrack.entity.Challan;
import com.example.challantrack.entity.Vehicle;
import com.example.challantrack.entity.ViolationType;
import com.example.challantrack.repository.ChallanRepository;
import com.example.challantrack.repository.VehicleRepository;
import com.example.challantrack.repository.ViolationTypeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChallanService {

    private final ChallanRepository challanRepository;
    private final VehicleRepository vehicleRepository;
    private final ViolationTypeRepository violationTypeRepository;

    public ChallanService(
            ChallanRepository challanRepository,
            VehicleRepository vehicleRepository,
            ViolationTypeRepository violationTypeRepository) {

        this.challanRepository = challanRepository;
        this.vehicleRepository = vehicleRepository;
        this.violationTypeRepository = violationTypeRepository;
    }

    // ==========================================
    // CREATE CHALLAN
    // ==========================================

    public Challan createChallan(Challan challan) {

        Long vehicleId = challan.getVehicle().getId();
        Long violationTypeId = challan.getViolationType().getId();

        // Find vehicle
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Vehicle not found with ID: " + vehicleId
                        )
                );

        // Find violation type
        ViolationType violationType =
                violationTypeRepository.findById(violationTypeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Violation type not found with ID: "
                                                + violationTypeId
                                )
                        );

        // Connect vehicle and violation type
        challan.setVehicle(vehicle);
        challan.setViolationType(violationType);

        // Set today's date if no date is provided
        if (challan.getChallanDate() == null) {
            challan.setChallanDate(LocalDate.now());
        }

        // ==========================================
        // CHECK REPEAT VIOLATION - ROLLING 12 MONTHS
        // ==========================================

        LocalDate endDate = challan.getChallanDate();
        LocalDate startDate = endDate.minusMonths(12);

        long previousViolations =
                challanRepository
                        .countByVehicleIdAndViolationTypeIdAndChallanDateBetween(
                                vehicleId,
                                violationTypeId,
                                startDate,
                                endDate
                        );

        // ==========================================
        // FINE ESCALATION
        // ==========================================

        double baseFine = violationType.getBaseFine();

        if (previousViolations == 0) {

            // First violation
            challan.setFineAmount(baseFine);

        } else if (previousViolations == 1) {

            // Second violation
            challan.setFineAmount(baseFine * 2);

        } else {

            // Third or more violation
            challan.setFineAmount(baseFine * 3);
        }

        // ==========================================
        // PAYMENT STATUS
        // ==========================================

        if (challan.getPaymentStatus() == null) {
            challan.setPaymentStatus("UNPAID");
        }

        // Save challan
        return challanRepository.save(challan);
    }

    // ==========================================
    // GET ALL CHALLANS
    // ==========================================

    public List<Challan> getAllChallans() {

        return challanRepository.findAll();
    }

    // ==========================================
    // GET CHALLAN BY ID
    // ==========================================

    public Challan getChallanById(Long id) {

        return challanRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Challan not found with ID: " + id
                        )
                );
    }

    // ==========================================
    // FIND OLD UNPAID CHALLANS
    // ==========================================

    public List<Challan> getOldUnpaidChallans() {

        // Calculate date 30 days before today
        LocalDate cutoffDate =
                LocalDate.now().minusDays(30);

        return challanRepository
                .findByPaymentStatusAndChallanDateBefore(
                        "UNPAID",
                        cutoffDate
                );
    }

    // ==========================================
    // UPDATE CHALLAN
    // ==========================================

    public Challan updateChallan(
            Long id,
            Challan challan) {

        Challan existingChallan =
                getChallanById(id);

        Long vehicleId =
                challan.getVehicle().getId();

        Long violationTypeId =
                challan.getViolationType().getId();

        // Find vehicle
        Vehicle vehicle =
                vehicleRepository.findById(vehicleId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vehicle not found with ID: "
                                                + vehicleId
                                )
                        );

        // Find violation type
        ViolationType violationType =
                violationTypeRepository.findById(violationTypeId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Violation type not found with ID: "
                                                + violationTypeId
                                )
                        );

        // Update vehicle
        existingChallan.setVehicle(vehicle);

        // Update violation type
        existingChallan.setViolationType(violationType);

        // Update fine
        existingChallan.setFineAmount(
                challan.getFineAmount()
        );

        // Update challan date
        existingChallan.setChallanDate(
                challan.getChallanDate()
        );

        // Update payment status
        existingChallan.setPaymentStatus(
                challan.getPaymentStatus()
        );

        // Update payment date
        existingChallan.setPaymentDate(
                challan.getPaymentDate()
        );

        return challanRepository.save(existingChallan);
    }

    // ==========================================
    // DELETE CHALLAN
    // ==========================================

    public void deleteChallan(Long id) {

        if (!challanRepository.existsById(id)) {

            throw new RuntimeException(
                    "Challan not found with ID: " + id
            );
        }

        challanRepository.deleteById(id);
    }
}