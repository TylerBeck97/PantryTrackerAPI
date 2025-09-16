package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Units;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitsRepository extends CrudRepository<Units, Long> {
    Optional<Units> findByUnitName(String unitName);
}
