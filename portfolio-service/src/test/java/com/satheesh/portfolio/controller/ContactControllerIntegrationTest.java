package com.satheesh.portfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.satheesh.portfolio.PortfolioServiceApplication;
import com.satheesh.portfolio.dto.ContactRequestDTO;
import com.satheesh.portfolio.dto.ContactResponseDTO;
import com.satheesh.portfolio.service.ContactService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Integration tests for ContactController using @SpringBootTest and MockMvc.
 */
@SpringBootTest(classes = PortfolioServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ContactControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactService contactService;

    @Test
    @DisplayName("POST /api/v1/contact — Should return 201 Created for valid payload")
    void testSubmitContactSuccess() throws Exception {
        ContactRequestDTO dto = new ContactRequestDTO("John Doe", "john@example.com", "Job Opportunity", "Hello Satheesh, great microservices portfolio!");
        ContactResponseDTO responseDTO = new ContactResponseDTO(1L, "Success", LocalDateTime.now());

        when(contactService.processContactSubmission(any(), anyString())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/v1/contact — Should return 400 Bad Request when XSS payload or invalid email is sent")
    void testSubmitContactValidationFailure() throws Exception {
        ContactRequestDTO invalidDto = new ContactRequestDTO("John", "invalid-email", "Hi", "<script>alert(1)</script>");

        mockMvc.perform(post("/api/v1/contact")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }
}
