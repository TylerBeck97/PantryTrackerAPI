package com.example.PantryTrackingAPI.dto;

import com.example.PantryTrackingAPI.entity.Unit;

public record UnitDTO(Long id, String unitName) {
    public static UnitDTO fromEntity(Unit unit){
        return new UnitDTO(
                unit.getId(),
                unit.getUnitName()
        );
    }
}
