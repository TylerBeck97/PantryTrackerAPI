package com.example.PantryTrackingAPI.repository;

import com.example.PantryTrackingAPI.entity.Brand;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BrandsRepository extends CrudRepository<Brand, Long> {
    Optional<Brand> findByBrandName(String brandName);
}
