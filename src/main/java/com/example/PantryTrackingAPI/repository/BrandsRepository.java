package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Brands;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BrandsRepository extends CrudRepository<Brands, Long> {
    Optional<Brands> findByBrandName(String brandName);
}
