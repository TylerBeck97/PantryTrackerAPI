package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Categories;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoriesRepository extends CrudRepository<Categories, Long> {
    Optional<Categories> findByCategoryName(String categoryName);
}
