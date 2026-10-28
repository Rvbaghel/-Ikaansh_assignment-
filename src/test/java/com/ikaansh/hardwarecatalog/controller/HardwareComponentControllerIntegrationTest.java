package com.ikaansh.hardwarecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ikaansh.hardwarecatalog.dto.request.HardwareComponentRequest;
import com.ikaansh.hardwarecatalog.dto.response.HardwareComponentResponse;
import com.ikaansh.hardwarecatalog.exception.ResourceNotFoundException;
import com.ikaansh.hardwarecatalog.service.HardwareComponentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HardwareComponentController.class)
class HardwareComponentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HardwareComponentService service;

    @Test
    @DisplayName("POST /api/v1/components returns 201 when request is valid")
    void createComponent_Valid_Returns201() throws Exception {
        HardwareComponentRequest request = HardwareComponentRequest.builder()
                .name("Corsair Vengeance 32GB DDR5")
                .category("RAM")
                .specifications("6000MHz CL30")
                .price(new BigDecimal("119.99"))
                .stockQuantity(25)
                .build();

        HardwareComponentResponse response = HardwareComponentResponse.builder()
                .id(1L)
                .name("Corsair Vengeance 32GB DDR5")
                .category("RAM")
                .specifications("6000MHz CL30")
                .price(new BigDecimal("119.99"))
                .stockQuantity(25)
                .build();

        when(service.createComponent(any(HardwareComponentRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/components")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Corsair Vengeance 32GB DDR5"))
                .andExpect(jsonPath("$.category").value("RAM"));
    }

    @Test
    @DisplayName("POST /api/v1/components returns 400 Bad Request when validation fails")
    void createComponent_Invalid_Returns400() throws Exception {
        HardwareComponentRequest invalidRequest = HardwareComponentRequest.builder()
                .name("") // Blank name
                .category("") // Blank category
                .price(new BigDecimal("-5.00")) // Negative price
                .stockQuantity(-1) // Negative quantity
                .build();

        mockMvc.perform(post("/api/v1/components")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.validationErrors.name").exists())
                .andExpect(jsonPath("$.validationErrors.category").exists())
                .andExpect(jsonPath("$.validationErrors.price").exists())
                .andExpect(jsonPath("$.validationErrors.stockQuantity").exists());
    }

    @Test
    @DisplayName("GET /api/v1/components/{id} returns 404 when component does not exist")
    void getComponentById_NotFound_Returns404() throws Exception {
        when(service.getComponentById(eq(999L)))
                .thenThrow(new ResourceNotFoundException("Hardware component not found with ID: 999"));

        mockMvc.perform(get("/api/v1/components/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Hardware component not found with ID: 999"));
    }
}