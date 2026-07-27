package com.satheesh.portfolio.controller;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.portfolio.dto.ProjectResponseDTO;
import com.satheesh.portfolio.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description REST Controller for querying portfolio projects.
 */
@RestController
@RequestMapping(AppConstants.PROJECTS_PATH)
@RequiredArgsConstructor
@Tag(name = "Projects API", description = "Endpoints for fetching portfolio projects and case studies")
public class ProjectController {

    private final ProjectService projectService;

    /**
     * Retrieves all featured projects for homepage display.
     * 
     * @return ResponseEntity containing List of ProjectResponseDTOs
     */
    @GetMapping
    @Operation(summary = "Get Featured Projects", description = "Returns featured active and in-progress portfolio projects")
    public ResponseEntity<List<ProjectResponseDTO>> getFeaturedProjects() {
        return ResponseEntity.ok(projectService.getFeaturedProjects());
    }

    /**
     * Retrieves a single project by ID.
     * 
     * @param id Project ID
     * @return ResponseEntity containing ProjectResponseDTO
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get Project by ID", description = "Returns detailed project information for a specific project ID")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }
}
