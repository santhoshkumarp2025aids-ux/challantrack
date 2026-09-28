package com.example.challantrack.service;

import com.example.challantrack.entity.Vehicle;
import com.example.challantrack.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    // Add vehicle
    public Vehicle createVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    // Get all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Get vehicle by ID
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Vehicle not found with ID: " + id
                        )
                );
    }

    // Update vehicle
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {

        Vehicle existingVehicle =
                getVehicleById(id);

        existingVehicle.setVehicleNumber(
                vehicle.getVehicleNumber()
        );

        existingVehicle.setOwnerName(
                vehicle.getOwnerName()
        );

        existingVehicle.setVehicleType(
                vehicle.getVehicleType()
        );

        return vehicleRepository.save(
                existingVehicle
        );
    }

    // Delete vehicle
    public void deleteVehicle(Long id) {

        if (!vehicleRepository.existsById(id)) {

            throw new RuntimeException(
                    "Vehicle not found with ID: " + id
            );
        }

        vehicleRepository.deleteById(id);
    }
}