package com.example.challantrack.controller;

import com.example.challantrack.entity.Challan;
import com.example.challantrack.service.ChallanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challans")
public class ChallanController {

    private final ChallanService challanService;

    public ChallanController(ChallanService challanService) {
        this.challanService = challanService;
    }

    // CREATE CHALLAN
    @PostMapping
    public ResponseEntity<Challan> createChallan(
            @Valid @RequestBody Challan challan) {

        Challan savedChallan =
                challanService.createChallan(challan);

        return ResponseEntity.ok(savedChallan);
    }

    // GET ALL CHALLANS
    @GetMapping
    public ResponseEntity<List<Challan>> getAllChallans() {

        return ResponseEntity.ok(
                challanService.getAllChallans()
        );
    }

    // GET OLD UNPAID CHALLANS
    @GetMapping("/old-unpaid")
    public ResponseEntity<List<Challan>> getOldUnpaidChallans() {

        return ResponseEntity.ok(
                challanService.getOldUnpaidChallans()
        );
    }

    // GET CHALLAN BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Challan> getChallanById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                challanService.getChallanById(id)
        );
    }

    // UPDATE CHALLAN
    @PutMapping("/{id}")
    public ResponseEntity<Challan> updateChallan(
            @PathVariable Long id,
            @Valid @RequestBody Challan challan) {

        Challan updatedChallan =
                challanService.updateChallan(
                        id,
                        challan
                );

        return ResponseEntity.ok(updatedChallan);
    }

    // DELETE CHALLAN
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChallan(
            @PathVariable Long id) {

        challanService.deleteChallan(id);

        return ResponseEntity.ok(
                "Challan deleted successfully"
        );
    }
}