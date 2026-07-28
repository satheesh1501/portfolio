package com.satheesh.portfolio.controller;

import com.satheesh.portfolio.PortfolioServiceApplication;
import com.satheesh.portfolio.dto.ProjectResponseDTO;
import com.satheesh.portfolio.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Integration tests for ProjectController using @SpringBootTest and MockMvc.
 */
@SpringBootTest(classes = PortfolioServiceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Test
    @DisplayName("GET /api/v1/projects — Should return 200 OK with list of featured projects")
    void testGetFeaturedProjects() throws Exception {
        ProjectResponseDTO p1 = new ProjectResponseDTO(1L, "Portfolio Microservices", "Desc", List.of("Java 21"), "http://github.com", null, null, true, "ACTIVE");
        ProjectResponseDTO p2 = new ProjectResponseDTO(2L, "Civil Platform", "Desc", List.of("React"), null, null, null, true, "IN_PROGRESS");

        when(projectService.getFeaturedProjects()).thenReturn(List.of(p1, p2));

        mockMvc.perform(get("/api/v1/projects")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Portfolio Microservices"))
                .andExpect(jsonPath("$[1].status").value("IN_PROGRESS"));
    }
}
