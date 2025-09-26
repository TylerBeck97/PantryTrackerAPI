package com.example.PantryTrackingAPI.controller;

import com.example.PantryTrackingAPI.dto.InventoryItemsDTO;
import com.example.PantryTrackingAPI.entity.*;
import com.example.PantryTrackingAPI.repository.BrandsRepository;
import com.example.PantryTrackingAPI.repository.CategoriesRepository;
import com.example.PantryTrackingAPI.repository.InventoryItemRepository;
import com.example.PantryTrackingAPI.repository.UnitsRepository;
import com.example.PantryTrackingAPI.security.CustomUserDetails;
import com.example.PantryTrackingAPI.security.CustomUserDetailsService;
import com.example.PantryTrackingAPI.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(InventoryItemController.class)
@Import(SecurityConfig.class)
class InventoryItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InventoryItemRepository repository;

    @MockitoBean
    private BrandsRepository brandsRepository;

    @MockitoBean
    private CategoriesRepository categoriesRepository;

    @MockitoBean
    private UnitsRepository unitsRepository;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    private InventoryItem sampleItem;
    private InventoryItemsDTO sampleDTO;
    private CustomUserDetails sampleUserDetails;

    @BeforeEach
    void setUp() {
        var barcode = "1111111111111";
        var description = "Test Item";
        var quantity = new BigDecimal(16);
        var updatedBy = "system";
        var brand = new Brand("Test Brand", updatedBy);
        var category = new Category("Test Category", updatedBy);
        var unit = new Unit("Test Unit", updatedBy);

        sampleItem = new InventoryItem(barcode, description, quantity, brand, category, unit, updatedBy);
        sampleItem.setId(1L); // This is needed for the PUT unit test
        sampleDTO = InventoryItemsDTO.fromEntity(sampleItem);

        var roles = Set.of(new Role("ROLE_USER", "system"), new Role("ROLE_ADMIN", "system"));
        var user = new User("test", "password", "test@email.com", "(555)-5555", roles, "system");
        sampleUserDetails = new CustomUserDetails(user);
    }

    @Test
    @WithMockUser
    void getInventoryItems() throws Exception {
        when(repository.findAll()).thenReturn(List.of(sampleItem));

        mockMvc.perform(get("/inventory-items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].barcode").value(sampleDTO.barcode()));
    }

    @Test
    @WithMockUser
    void getInventoryItemById() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleItem));

        mockMvc.perform(get("/inventory-items/id/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value(sampleDTO.barcode()));
    }

    @Test
    @WithMockUser
    void getInventoryItemByBarcode() throws Exception {
        when(repository.findByBarcode("0005342000011")).thenReturn(Optional.of(sampleItem));

        mockMvc.perform(get("/inventory-items/barcode/0005342000011"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value(sampleDTO.brand()));
    }

    @Test
    void postInventoryItem() throws Exception {
        when(repository.save(any())).thenReturn(sampleItem);

        mockMvc.perform(post("/inventory-items")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                sampleUserDetails,
                                null,
                                sampleUserDetails.getAuthorities()
                        )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.barcode").value(sampleDTO.barcode()));
    }

    @Test
    void putInventoryItem() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleItem));
        when(repository.save(any())).thenReturn(sampleItem);

        mockMvc.perform(put("/inventory-items/id/1")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                sampleUserDetails,
                                null,
                                sampleUserDetails.getAuthorities()
                        )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantity").value(sampleDTO.quantity()));
    }

    @Test
    void putInventoryItemWrongId() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleItem));
        when(repository.save(any())).thenReturn(sampleItem);

        mockMvc.perform(put("/inventory-items/id/2")
                        .with(authentication(new UsernamePasswordAuthenticationToken(
                                sampleUserDetails,
                                null,
                                sampleUserDetails.getAuthorities()
                        )))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteInventoryItem() throws Exception{
        when(repository.findById(1L)).thenReturn(Optional.of(sampleItem));

        mockMvc.perform(delete("/inventory-items/id/1"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteInventoryItemIDNotFound() throws Exception{
        when(repository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/inventory-items/id/1"))
                .andExpect(status().isNotFound());
    }
}