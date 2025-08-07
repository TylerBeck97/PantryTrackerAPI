package com.example.PantryTrackingAPI.data;

import com.example.PantryTrackingAPI.entity.InventoryItem;
import com.example.PantryTrackingAPI.entity.Role;
import com.example.PantryTrackingAPI.entity.User;
import com.example.PantryTrackingAPI.enums.Units;
import com.example.PantryTrackingAPI.repository.InventoryItemRepository;
import com.example.PantryTrackingAPI.repository.RoleRepository;
import com.example.PantryTrackingAPI.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader {
    private final InventoryItemRepository inventoryItemRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(InventoryItemRepository inventoryItemRepository, UserRepository userRepository,
                      RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.inventoryItemRepository = inventoryItemRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void loadData(){
        var testDate = LocalDate.of(2025,8,31);

        roleRepository.saveAll(List.of(
                new Role("user"),
                new Role("admin")
        ));

        userRepository.saveAll(List.of(
                new User("user", "example@email.com", "555-555-5555", passwordEncoder.encode("password"), roleRepository.findByName("user")),
                new User("admin", "admin@email.com", "999-999-9999", passwordEncoder.encode("password"), roleRepository.findByName("admin"))
        ));

        inventoryItemRepository.saveAll(List.of(
                new InventoryItem("Ziti", List.of("111111111111"), 2.0f, Units.KG, testDate, userRepository.findByUsername("user")),
                new InventoryItem("Butter", List.of("222222222222"), 2.0f, Units.G, testDate, userRepository.findByUsername("admin")),
                new InventoryItem("Lettuce", List.of("333333333333"), 2.0f, Units.POUND, testDate, userRepository.findByUsername("admin"))
        ));
    }

}
