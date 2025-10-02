package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.InventoryItemDTO;
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
    private final InventoryItemRepository inventoryItemRepository;
    private final BrandsRepository brandsRepository;
    private final CategoriesRepository categoriesRepository;
    private final UnitsRepository unitsRepository;

    public InventoryItemController(InventoryItemRepository inventoryItemRepository, BrandsRepository brandsRepository, CategoriesRepository categoriesRepository, UnitsRepository unitsRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
        this.unitsRepository = unitsRepository;
    }

    @GetMapping
    Iterable<InventoryItemDTO> getInventoryItems(){
        return inventoryItemRepository.findAll()
                .stream()
                .map(InventoryItemDTO::fromEntity)
                .toList();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<InventoryItemDTO> getInventoryItemById(
            @PathVariable long id) {
        return inventoryItemRepository.findById(id)
                .map(InventoryItemDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<InventoryItemDTO> getInventoryItemByBarcode(
            @PathVariable String barcode) {
        return inventoryItemRepository.findByBarcode(barcode)
                .map(InventoryItemDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<InventoryItemDTO> postInventoryItem(
            @RequestBody InventoryItemDTO request,
            @AuthenticationPrincipal CustomUserDetails principal){
        var item = inventoryItemsFromRequest(request, principal.getUsername());

        return new ResponseEntity<>(InventoryItemDTO.fromEntity(inventoryItemRepository.save(item)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/id/{id}")
    public ResponseEntity<InventoryItemDTO> putInventoryItem(
            @PathVariable long id,
            @RequestBody InventoryItemDTO request,
            @AuthenticationPrincipal CustomUserDetails principal) {
        if (id != request.id()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and payload do not match");
        }

        var item = inventoryItemsFromRequest(request, principal.getUsername());

        return inventoryItemRepository.findById(id)
                .map(existing -> new ResponseEntity<>(InventoryItemDTO.fromEntity(inventoryItemRepository.save(item)), HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteInventoryItem(@PathVariable long id) {
        var item = inventoryItemRepository.findById(id).orElse(null);

        if (item == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found");
        }

        inventoryItemRepository.delete(item);
        return ResponseEntity.ok().build();
    }

    private InventoryItem inventoryItemsFromRequest(InventoryItemDTO request, String username){
        var item = new InventoryItem();
        item.setId(request.id());
        item.setDescription(request.description());
        item.setBarcode(request.barcode());
        item.setQuantity(request.quantity());

        // Finds brand by name or creates a new one if it doesn't exist
        var brand = brandsRepository.findByBrandName(request.brand())
                .orElseGet(() -> brandsRepository.save(new Brand(request.brand(), username)));
        item.setBrand(brand);

        // Same for categories
        var category = categoriesRepository.findByCategoryName(request.category())
                .orElseGet(() -> categoriesRepository.save(new Category(request.category(), username)));
        item.setCategory(category);

        // Same for units
        var unit = unitsRepository.findByUnitName(request.unit())
                .orElseGet(() -> unitsRepository.save(new Unit(request.unit(), username)));
        item.setUnit(unit);
        item.setUpdatedBy(username);

        return item;
    }
}