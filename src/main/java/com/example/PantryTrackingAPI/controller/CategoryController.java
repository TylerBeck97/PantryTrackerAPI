package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.CategoryDTO;
import com.example.PantryTrackingAPI.entity.Category;
import com.example.PantryTrackingAPI.repository.CategoriesRepository;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoriesRepository categoriesRepository;

    public CategoryController(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping
    Iterable<CategoryDTO> getCategories(){
        return categoriesRepository.findAll()
                .stream()
                .map(CategoryDTO::fromEntity)
                .toList();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(
            @PathVariable long id){
        return categoriesRepository.findById(id)
                .map(CategoryDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryDTO> postCategory(
            @RequestBody CategoryDTO request,
            @AuthenticationPrincipal CustomUserDetails principal){
        var category = new Category(request.categoryName(), principal.getUsername());

        return new ResponseEntity<>(CategoryDTO.fromEntity(categoriesRepository.save(category)), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("id/{id}")
    public ResponseEntity<CategoryDTO> putCategory(
            @PathVariable long id,
            @RequestBody CategoryDTO request,
            @AuthenticationPrincipal CustomUserDetails principal){
        if (id != request.id()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in path and payload do not match");
        }

        var category = new Category(request.categoryName(), principal.getUsername());
        category.setId(request.id());

        return categoriesRepository.findById(id)
                .map(existing -> new ResponseEntity<>(CategoryDTO.fromEntity(categoriesRepository.save(category)), HttpStatus.OK))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable long id){
        var category = categoriesRepository.findById(id).orElse(null);

        if (category == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID not found");
        }

        categoriesRepository.delete(category);
        return ResponseEntity.ok().build();
    }
}
