package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Unit;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UnitsRepository extends CrudRepository<Unit, Long> {
    Optional<Unit> findByUnitName(String unitName);
}
