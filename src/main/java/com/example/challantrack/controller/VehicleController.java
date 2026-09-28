package com.example.challantrack.controller;

import com.example.challantrack.entity.Vehicle;
import com.example.challantrack.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // CREATE VEHICLE
    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(
            @Valid @RequestBody Vehicle vehicle) {

        Vehicle savedVehicle =
                vehicleService.createVehicle(vehicle);

        return ResponseEntity.ok(savedVehicle);
    }

    // GET ALL VEHICLES
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {

        List<Vehicle> vehicles =
                vehicleService.getAllVehicles();

        return ResponseEntity.ok(vehicles);
    }

    // GET VEHICLE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(
            @PathVariable Long id) {

        Vehicle vehicle =
                vehicleService.getVehicleById(id);

        return ResponseEntity.ok(vehicle);
    }

    // UPDATE VEHICLE
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody Vehicle vehicle) {

        Vehicle updatedVehicle =
                vehicleService.updateVehicle(
                        id,
                        vehicle
                );

        return ResponseEntity.ok(updatedVehicle);
    }

    // DELETE VEHICLE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(
            @PathVariable Long id) {

        vehicleService.deleteVehicle(id);

        return ResponseEntity.ok(
                "Vehicle deleted successfully"
        );
    }
}