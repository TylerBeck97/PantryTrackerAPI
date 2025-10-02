package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.UserInventoryItemDTO;
import com.example.PantryTrackingAPI.entity.*;
import com.example.PantryTrackingAPI.repository.InventoryItemRepository;
import com.example.PantryTrackingAPI.repository.UsersInventoryItemRepository;
import com.example.PantryTrackingAPI.request.UserInventoryItemRequest;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import com.example.PantryTrackingAPI.security.CustomUserDetailsService;
import com.example.PantryTrackingAPI.security.SecurityConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserInventoryItemController.class)
@Import(SecurityConfig.class)
class UserInventoryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersInventoryItemRepository usersInventoryItemRepository;

    @MockitoBean
    private InventoryItemRepository inventoryItemRepository;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private CustomUserDetails sampleUserDetails;
    private InventoryItem sampleInventoryItem;
    private UserInventoryItem sampleUserInventoryItem;
    private String expectedJson;
    private String expectedJsonList;
    private String jsonRequest;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        var barcode = "1111111111111";
        var description = "Test Item";
        var quantity = new BigDecimal(16);
        var updatedBy = "system";
        var brand = new Brand("Test Brand", updatedBy);
        var category = new Category("Test Category", updatedBy);
        var unit = new Unit("Test Unit", updatedBy);

        sampleInventoryItem = new InventoryItem(barcode,description,quantity,brand,category,unit, updatedBy);

        var purchaseDate = LocalDate.now();
        var useByDate = LocalDate.now().plusDays(30);

        var roles = Set.of(new Role("ROLE_USER", "system"), new Role("ROLE_ADMIN", "system"));
        var user = new User("test", "password", "test@email.com", "(555)-5555", roles, "system");
        sampleUserDetails = new CustomUserDetails(user);
        sampleUserInventoryItem = new UserInventoryItem(purchaseDate, useByDate, user, sampleInventoryItem, updatedBy);
        sampleUserInventoryItem.setId(1L);

        var userInventoryItemDTO = UserInventoryItemDTO.fromEntity(sampleUserInventoryItem);

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        expectedJson = objectMapper.writeValueAsString(userInventoryItemDTO);
        expectedJsonList = objectMapper.writeValueAsString(List.of(userInventoryItemDTO));

        var userInventoryItemRequest = new UserInventoryItemRequest(
                sampleUserInventoryItem.getId(),
                sampleUserInventoryItem.getPurchaseDate(),
                sampleUserInventoryItem.getUseByDate(),
                sampleUserInventoryItem.getRemainingQuantity(),
                sampleUserInventoryItem.getInventoryItem().getBarcode());

        jsonRequest = objectMapper.writeValueAsString(userInventoryItemRequest);
    }

    @Test
    void getUsersInventoryItem() throws Exception{
        when(usersInventoryItemRepository.findByUserUsername("test")).thenReturn(List.of(sampleUserInventoryItem));

        mockMvc.perform(get("/user-inventory-items")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                sampleUserDetails,
                                null,
                                sampleUserDetails.getAuthorities()
                        ))))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJsonList));
    }

    @Test
    void getUsersInventoryItemById() throws Exception{
        when(usersInventoryItemRepository.findById(1L)).thenReturn(Optional.of(sampleUserInventoryItem));

        mockMvc.perform(get("/user-inventory-items/id/1")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                sampleUserDetails,
                                null,
                                sampleUserDetails.getAuthorities()
                        ))))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void postUserInventoryItem() throws Exception {
        when(usersInventoryItemRepository.save(any())).thenReturn(sampleUserInventoryItem);
        when(inventoryItemRepository.findByBarcode(any())).thenReturn(Optional.of(sampleInventoryItem));

        mockMvc.perform(post("/user-inventory-items")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                sampleUserDetails,
                                null,
                                sampleUserDetails.getAuthorities()
                        )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void putUserInventoryItem() throws Exception {
        when(usersInventoryItemRepository.findById(1L)).thenReturn(Optional.of(sampleUserInventoryItem));
        when(usersInventoryItemRepository.save(any())).thenReturn(sampleUserInventoryItem);
        when(inventoryItemRepository.findByBarcode(any())).thenReturn(Optional.of(sampleInventoryItem));

        mockMvc.perform(put("/user-inventory-items/id/1")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                sampleUserDetails,
                                null,
                                sampleUserDetails.getAuthorities()
                        )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void deleteUserInventoryItem() throws Exception {
        when(usersInventoryItemRepository.findById(1L)).thenReturn(Optional.of(sampleUserInventoryItem));

        mockMvc.perform(delete("/user-inventory-items/id/1")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                sampleUserDetails,
                                null,
                                sampleUserDetails.getAuthorities()
                        ))))
                .andExpect(status().isOk());
    }
}