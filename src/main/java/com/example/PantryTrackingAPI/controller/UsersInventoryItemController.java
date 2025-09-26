package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.UsersInventoryItemDTO;
import com.example.PantryTrackingAPI.entity.UserInventoryItem;
import com.example.PantryTrackingAPI.repository.UsersInventoryItemRepository;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user-inventory-items")
public class UsersInventoryItemController {
    private final UsersInventoryItemRepository repository;

    public UsersInventoryItemController(UsersInventoryItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    Iterable<UsersInventoryItemDTO> getUsersInventoryItem(@AuthenticationPrincipal CustomUserDetails principal){
        return repository.findByUserUsername(principal.getUsername())
                .stream()
                .map(UsersInventoryItemDTO::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersInventoryItemDTO> getUsersInventoryItemById(
            @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails principal) {

        return repository.findById(id)
                .map(UsersInventoryItemDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @GetMapping("/{barcode}")
    public ResponseEntity<UsersInventoryItemDTO> getUsersInventoryItemByBarcode(
            @PathVariable String barcode,
            @AuthenticationPrincipal CustomUserDetails principal) {
        return repository.findByInventoryItemBarcodeAndUser(barcode, principal.getUser())
                .map(UsersInventoryItemDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @PostMapping
    public ResponseEntity<UsersInventoryItemDTO> postUserInventoryItem(@RequestBody UserInventoryItem item, @AuthenticationPrincipal CustomUserDetails principal){
        item.setUser(principal.getUser());
        item.setUpdatedBy(principal.getUsername());
        return new ResponseEntity<>(UsersInventoryItemDTO.fromEntity(repository.save(item)), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersInventoryItemDTO> putUserInventoryItem(
            @PathVariable long id,
            @RequestBody UserInventoryItem item,
            @AuthenticationPrincipal CustomUserDetails principal) {

        if (id != item.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and payload do not match");
        }

        item.setUpdatedBy(principal.getUsername());

        return repository.findById(id)
                .filter(existing -> existing.getUser().equals(principal.getUser()))
                .map(existing -> {
                    return new ResponseEntity<>(UsersInventoryItemDTO.fromEntity(repository.save(item)), HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteUserInventoryItem(
            @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails principal) {

        UserInventoryItem item = repository.findById(id)
                .filter(i -> i.getUser().equals(principal.getUser()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Access denied or item not found"));

        repository.delete(item);
    }
}