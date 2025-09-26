package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoriesRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
}
