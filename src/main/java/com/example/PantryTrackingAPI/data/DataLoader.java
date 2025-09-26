package com.example.PantryTrackingAPI.data;

import com.example.PantryTrackingAPI.entity.*;
import com.example.PantryTrackingAPI.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataLoader {
    private final BrandsRepository brandsRepository;
    private final CategoriesRepository categoriesRepository;
    private final UnitsRepository unitsRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final UsersInventoryItemRepository usersInventoryItemRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(BrandsRepository brandsRepository, CategoriesRepository categoriesRepository,
                      UnitsRepository unitsRepository, InventoryItemRepository inventoryItemRepository,
                      UsersInventoryItemRepository usersInventoryItemRepository, UserRepository userRepository,
                      RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
        this.unitsRepository = unitsRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.usersInventoryItemRepository = usersInventoryItemRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void loadData(){
        roleRepository.saveAll(List.of(
                new Role("ROLE_USER", "system"),
                new Role("ROLE_ADMIN", "system")
        ));

        userRepository.saveAll(List.of(
                new User("user", passwordEncoder.encode("password"), "user@email.com", "(919)-555-555",
                        roleRepository.findByName("ROLE_USER")
                                .map(Collections::singleton)
                                .orElse(Collections.emptySet()),
                        "system"),

                new User("admin", passwordEncoder.encode("password"), "admin@email.com", "(919)-444-4444",
                        Stream.of("ROLE_USER", "ROLE_ADMIN")
                                .map(roleRepository::findByName)
                                .flatMap(Optional::stream)
                                .collect(Collectors.toSet()),
                        "system")

        ));

        brandsRepository.saveAll(List.of(
                new Brand("Daisy", "system")
        ));

        categoriesRepository.saveAll(List.of(
                new Category("Dairy", "system"),
                new Category("Meat", "system"),
                new Category("Produce", "system"),
                new Category("Staples", "system"),
                new Category("Grain", "system"),
                new Category("Drinks", "system")
        ));

        var units = new String[]{"mL", "L", "tsp", "tbsp", "fl oz",
                "cup", "pint", "quart", "gallon", "mg", "g", "kg", "lb", "oz"};
        var list = new ArrayList<Unit>();
        for (String unit : units){
            list.add(new Unit(unit, "system"));
        }
        unitsRepository.saveAll(list);

        inventoryItemRepository.saveAll(List.of(
                new InventoryItem("0007342000011", "Pure and Natural Sour Cream", new BigDecimal(16),
                        brandsRepository.findByBrandName("Daisy")
                                .orElseThrow(() -> new IllegalArgumentException("Missing Brand")),
                        categoriesRepository.findByCategoryName("Dairy")
                                .orElseThrow(() -> new IllegalArgumentException("Missing Category")),
                        unitsRepository.findByUnitName("oz")
                                .orElseThrow(() -> new IllegalArgumentException("Missing Unit")),
                        "system")
        ));

        usersInventoryItemRepository.saveAll(List.of(
                new UserInventoryItem(LocalDate.now(), LocalDate.now().plusDays(30),
                        userRepository.findByUsername("user"),
                        inventoryItemRepository.findByBarcode("0007342000011")
                                .orElseThrow(() -> new IllegalArgumentException("Missing Inventory Item")),
                        "system")
        ));
    }
}
