package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.UserInventoryItemDTO;
import com.example.PantryTrackingAPI.entity.UserInventoryItem;
import com.example.PantryTrackingAPI.repository.InventoryItemRepository;
import com.example.PantryTrackingAPI.repository.UsersInventoryItemRepository;
import com.example.PantryTrackingAPI.request.UserInventoryItemRequest;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user-inventory-items")
public class UserInventoryItemController {
    private final UsersInventoryItemRepository usersInventoryItemRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public UserInventoryItemController(UsersInventoryItemRepository repository, InventoryItemRepository inventoryItemRepository) {
        this.usersInventoryItemRepository = repository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    @GetMapping
    Iterable<UserInventoryItemDTO> getUsersInventoryItem(
            @AuthenticationPrincipal CustomUserDetails principal){
        return usersInventoryItemRepository.findByUserUsername(principal.getUsername())
                .stream()
                .map(UserInventoryItemDTO::fromEntity)
                .toList();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserInventoryItemDTO> getUsersInventoryItemById(
            @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails principal) {

        return usersInventoryItemRepository.findById(id)
                .filter(item -> item.getUser().equals(principal.getUser()))
                .map(UserInventoryItemDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @PostMapping
    public ResponseEntity<UserInventoryItemDTO> postUserInventoryItem(
            @RequestBody UserInventoryItemRequest request,
            @AuthenticationPrincipal CustomUserDetails principal){
        var item = userInventoryItemFromRequest(request, principal);

        if (item == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed request");

        return new ResponseEntity<>(UserInventoryItemDTO.fromEntity(usersInventoryItemRepository.save(item)), HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<UserInventoryItemDTO> putUserInventoryItem(
            @PathVariable long id,
            @RequestBody UserInventoryItemRequest request,
            @AuthenticationPrincipal CustomUserDetails principal) {

        var item = userInventoryItemFromRequest(request, principal);
        if (item == null || item.getId() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Malformed request");
        }

        if (id != item.getId() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and payload do not match");
        }

        return usersInventoryItemRepository.findById(id)
                .filter(existing -> existing.getUser().equals(principal.getUser()))
                .map(existing -> new ResponseEntity<>(UserInventoryItemDTO.fromEntity(usersInventoryItemRepository.save(item)), HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied or item not found"));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteUserInventoryItem(
            @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails principal) {

        UserInventoryItem item = usersInventoryItemRepository.findById(id)
                .filter(i -> i.getUser().equals(principal.getUser()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.FORBIDDEN, "Access denied or item not found"));

        usersInventoryItemRepository.delete(item);
        return ResponseEntity.ok().build();
    }

    private UserInventoryItem userInventoryItemFromRequest(UserInventoryItemRequest request, CustomUserDetails principle){
        var userItem = new UserInventoryItem();

        userItem.setId(request.id());
        userItem.setPurchaseDate(request.purchaseDate());
        userItem.setUseByDate(request.useByDate());
        userItem.setUser(principle.getUser());
        userItem.setUpdatedBy(principle.getUsername());

        var inventoryItem = inventoryItemRepository.findByBarcode(request.inventoryItemBarcode());
        if (inventoryItem.isEmpty())
            return null;

        if (request.remainingQuantity() != null)
            userItem.setRemainingQuantity(request.remainingQuantity());
        else
            userItem.setRemainingQuantity(inventoryItem.get().getQuantity());

        userItem.setInventoryItem(inventoryItem.get());
        return userItem;
    }
}