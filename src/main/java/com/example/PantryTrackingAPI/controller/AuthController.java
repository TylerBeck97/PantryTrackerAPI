package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.repository.RoleRepository;
import com.example.PantryTrackingAPI.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/check")
    public ResponseEntity<Void> checkAuth() {
        return ResponseEntity.ok().build();
    }
}

    /*@PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.existsByUsernameOrEmail(request.username(), request.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username or email already in use");
        }

        User newuser = new User(
                request.username(),
                request.email(),
                request.phoneNumber(),
                passwordEncoder.encode(request.password()),
                roleRepository.findByName("user")
        );

        userRepository.save(newuser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

     */
