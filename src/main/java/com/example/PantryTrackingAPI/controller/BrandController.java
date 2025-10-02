package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.BrandDTO;
import com.example.PantryTrackingAPI.entity.Brand;
import com.example.PantryTrackingAPI.repository.BrandsRepository;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/brands")
public class BrandController {
    private final BrandsRepository brandsRepository;

    public BrandController(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    @GetMapping
    Iterable<BrandDTO> getBrands(){
        return brandsRepository.findAll()
                .stream()
                .map(BrandDTO::fromEntity)
                .toList();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<BrandDTO> getBrandById(
            @PathVariable long id){
        return brandsRepository.findById(id)
                .map(BrandDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BrandDTO> postBrand(
            @RequestBody BrandDTO request,
            @AuthenticationPrincipal CustomUserDetails principal){
        var brand = new Brand(request.brandName(), principal.getUsername());

        return new ResponseEntity<>(BrandDTO.fromEntity(brandsRepository.save(brand)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("id/{id}")
    public ResponseEntity<BrandDTO> putBrand(
            @PathVariable long id,
            @RequestBody BrandDTO request,
            @AuthenticationPrincipal CustomUserDetails principal){
        if (id != request.id()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and payload do not match");
        }

        var brand = new Brand(request.brandName(), principal.getUsername());
        brand.setId(request.id());

        return brandsRepository.findById(id)
                .map(existing -> new ResponseEntity<>(BrandDTO.fromEntity(brandsRepository.save(brand)), HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteBrand(
            @PathVariable long id){
        var brand = brandsRepository.findById(id).orElse(null);

        if (brand == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found");
        }

        brandsRepository.delete(brand);
        return ResponseEntity.ok().build();
    }
}
