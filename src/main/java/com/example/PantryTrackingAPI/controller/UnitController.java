package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.UnitDTO;
import com.example.PantryTrackingAPI.entity.Unit;
import com.example.PantryTrackingAPI.repository.UnitsRepository;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/units")
public class UnitController {
    private final UnitsRepository unitsRepository;

    public UnitController(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    @GetMapping
    Iterable<UnitDTO> getCategories(){
        return unitsRepository.findAll()
                .stream()
                .map(UnitDTO::fromEntity)
                .toList();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UnitDTO> getCategoryById(
            @PathVariable long id){
        return unitsRepository.findById(id)
                .map(UnitDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UnitDTO> postCategory(
            @RequestBody UnitDTO request,
            @AuthenticationPrincipal CustomUserDetails principal){
        var units = new Unit(request.unitName(), principal.getUsername());

        return new ResponseEntity<>(UnitDTO.fromEntity(unitsRepository.save(units)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("id/{id}")
    public ResponseEntity<UnitDTO> putCategory(
            @PathVariable long id,
            @RequestBody UnitDTO request,
            @AuthenticationPrincipal CustomUserDetails principal){
        if (id != request.id()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and payload do not match");
        }

        var units = new Unit(request.unitName(), principal.getUsername());
        units.setId(request.id());

        return unitsRepository.findById(id)
                .map(existing -> new ResponseEntity<>(UnitDTO.fromEntity(unitsRepository.save(units)), HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable long id){
        var category = unitsRepository.findById(id).orElse(null);

        if (category == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found");
        }

        unitsRepository.delete(category);
        return ResponseEntity.ok().build();
    }
}