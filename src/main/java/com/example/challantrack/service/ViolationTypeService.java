package com.example.challantrack.service;

import com.example.challantrack.entity.ViolationType;
import com.example.challantrack.repository.ViolationTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViolationTypeService {

    private final ViolationTypeRepository violationTypeRepository;

    public ViolationTypeService(
            ViolationTypeRepository violationTypeRepository) {
        this.violationTypeRepository = violationTypeRepository;
    }

    public ViolationType createViolationType(
            ViolationType violationType) {

        return violationTypeRepository.save(violationType);
    }

    public List<ViolationType> getAllViolationTypes() {

        return violationTypeRepository.findAll();
    }

    public ViolationType getViolationTypeById(Long id) {

        return violationTypeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Violation type not found with ID: " + id
                        )
                );
    }

    public ViolationType updateViolationType(
            Long id,
            ViolationType violationType) {

        ViolationType existing =
                getViolationTypeById(id);

        existing.setViolationName(
                violationType.getViolationName()
        );

        existing.setBaseFine(
                violationType.getBaseFine()
        );

        return violationTypeRepository.save(existing);
    }

    public void deleteViolationType(Long id) {

        if (!violationTypeRepository.existsById(id)) {
            throw new RuntimeException(
                    "Violation type not found with ID: " + id
            );
        }

        violationTypeRepository.deleteById(id);
    }
}