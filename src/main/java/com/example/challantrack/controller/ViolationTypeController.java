package com.example.challantrack.controller;

import com.example.challantrack.entity.ViolationType;
import com.example.challantrack.service.ViolationTypeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/violation-types")
public class ViolationTypeController {

    private final ViolationTypeService violationTypeService;

    public ViolationTypeController(
            ViolationTypeService violationTypeService) {

        this.violationTypeService = violationTypeService;
    }

    // CREATE VIOLATION TYPE
    @PostMapping
    public ResponseEntity<ViolationType> createViolationType(
            @Valid @RequestBody ViolationType violationType) {

        return ResponseEntity.ok(
                violationTypeService.createViolationType(
                        violationType
                )
        );
    }

    // GET ALL VIOLATION TYPES
    @GetMapping
    public ResponseEntity<List<ViolationType>> getAllViolationTypes() {

        return ResponseEntity.ok(
                violationTypeService.getAllViolationTypes()
        );
    }

    // GET VIOLATION TYPE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ViolationType> getViolationTypeById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                violationTypeService.getViolationTypeById(id)
        );
    }

    // UPDATE VIOLATION TYPE
    @PutMapping("/{id}")
    public ResponseEntity<ViolationType> updateViolationType(
            @PathVariable Long id,
            @Valid @RequestBody ViolationType violationType) {

        return ResponseEntity.ok(
                violationTypeService.updateViolationType(
                        id,
                        violationType
                )
        );
    }

    // DELETE VIOLATION TYPE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteViolationType(
            @PathVariable Long id) {

        violationTypeService.deleteViolationType(id);

        return ResponseEntity.ok(
                "Violation type deleted successfully"
        );
    }
}