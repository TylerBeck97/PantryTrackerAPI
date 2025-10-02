package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitsRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findByUnitName(String unitName);
}
