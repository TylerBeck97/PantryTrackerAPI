package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.InventoryItemsDTO;
import com.example.PantryTrackingAPI.entity.Brand;
import com.example.PantryTrackingAPI.entity.Category;
import com.example.PantryTrackingAPI.entity.InventoryItem;
import com.example.PantryTrackingAPI.entity.Unit;
import com.example.PantryTrackingAPI.repository.BrandsRepository;
import com.example.PantryTrackingAPI.repository.CategoriesRepository;
import com.example.PantryTrackingAPI.repository.InventoryItemRepository;
import com.example.PantryTrackingAPI.repository.UnitsRepository;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/inventory-items")
public class InventoryItemController {
    private final InventoryItemRepository repository;
    private final BrandsRepository brandsRepository;
    private final CategoriesRepository categoriesRepository;
    private final UnitsRepository unitsRepository;

    public InventoryItemController(InventoryItemRepository repository, BrandsRepository brandsRepository, CategoriesRepository categoriesRepository, UnitsRepository unitsRepository) {
        this.repository = repository;
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
        this.unitsRepository = unitsRepository;
    }

    @GetMapping
    Iterable<InventoryItemsDTO> getInventoryItems(){
        return repository.findAll()
                .stream()
                .map(InventoryItemsDTO::fromEntity)
                .toList();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<InventoryItemsDTO> getInventoryItemById(
            @PathVariable long id) {
        return repository.findById(id)
                .map(InventoryItemsDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<InventoryItemsDTO> getInventoryItemByBarcode(
            @PathVariable String barcode) {
        return repository.findByBarcode(barcode)
                .map(InventoryItemsDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<InventoryItemsDTO> postInventoryItem(
            @RequestBody InventoryItemsDTO request,
            @AuthenticationPrincipal CustomUserDetails principal){
        var item = inventoryItemsFromRequest(request, principal.getUsername());

        return new ResponseEntity<>(InventoryItemsDTO.fromEntity(repository.save(item)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/id/{id}")
    public ResponseEntity<InventoryItemsDTO> putInventoryItem(
            @PathVariable long id,
            @RequestBody InventoryItemsDTO request,
            @AuthenticationPrincipal CustomUserDetails principal) {
        if (id != request.id()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and payload do not match");
        }

        var item = inventoryItemsFromRequest(request, principal.getUsername());

        return repository.findById(id)
                .map(existing -> new ResponseEntity<>(InventoryItemsDTO.fromEntity(repository.save(item)), HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable long id) {

        var item = repository.findById(id).orElse(null);

        if (item != null){
            repository.delete(item);
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    private InventoryItem inventoryItemsFromRequest(InventoryItemsDTO request, String username){
        var item = new InventoryItem();
        item.setId(request.id());
        item.setDescription(request.description());
        item.setBarcode(request.barcode());
        item.setQuantity(request.quantity());

        // Finds brand by name or creates a new one if it doesn't exist
        Brand brand = brandsRepository.findByBrandName(request.brand())
                .orElseGet(() -> brandsRepository.save(new Brand(request.brand(), username)));
        item.setBrand(brand);

        // Same for categories
        Category category = categoriesRepository.findByCategoryName(request.category())
                .orElseGet(() -> categoriesRepository.save(new Category(request.category(), username)));
        item.setCategory(category);

        // Same for units
        Unit unit = unitsRepository.findByUnitName(request.unit())
                .orElseGet(() -> unitsRepository.save(new Unit(request.unit(), username)));
        item.setUnit(unit);
        item.setUpdatedBy(username);

        return item;
    }
}