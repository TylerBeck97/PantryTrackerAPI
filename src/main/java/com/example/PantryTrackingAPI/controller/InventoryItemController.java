package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.entity.InventoryItems;
import com.example.PantryTrackingAPI.repository.InventoryItemRepository;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/*
@RestController
@RequestMapping("/inventory-items")
public class InventoryItemController {
    private final InventoryItemRepository repository;

    public InventoryItemController(InventoryItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    Iterable<InventoryItemDTO> getInventoryItems(@AuthenticationPrincipal CustomUserDetails principal){
        return repository.findByOwnerUsername(principal.getUsername())
                .stream()
                .map(InventoryItemDTO::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryItemDTO> getInventoryItemById(
            @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails principal) {

        return repository.findById(id)
                .map(InventoryItemDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @GetMapping("/{barcode}")
    public ResponseEntity<InventoryItemDTO> getInventoryItemByBarcode(
            @PathVariable String barcode,
            @AuthenticationPrincipal CustomUserDetails principal) {
        return repository.findByBarcodeAndOwner(barcode, principal.getUser())
                .map(InventoryItemDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }



    @PostMapping
    InventoryItemDTO postInventoryItem(@RequestBody InventoryItem item, @AuthenticationPrincipal CustomUserDetails principal){
        item.setOwner(principal.getUser());
        return InventoryItemDTO.fromEntity(repository.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryItemDTO> putInventoryItem(
            @PathVariable long id,
            @RequestBody InventoryItem item,
            @AuthenticationPrincipal CustomUserDetails principal) {

        if (id != item.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and payload do not match");
        }

        return repository.findById(id)
                .filter(existing -> existing.getOwner().equals(principal.getUser()))
                .map(existing -> {
                    item.setOwner(principal.getUser()); // Ensure ownership isn't overwritten
                    return new ResponseEntity<>(InventoryItemDTO.fromEntity(repository.save(item)), HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteInventoryItem(
            @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails principal) {

        InventoryItem item = repository.findById(id)
                .filter(i -> i.getOwner().equals(principal.getUser()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Access denied or item not found"));

        repository.delete(item);
    }
}

 */
