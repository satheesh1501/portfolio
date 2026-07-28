package com.satheesh.portfolio.service;

import com.satheesh.portfolio.dto.ProjectResponseDTO;

import java.util.List;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service interface for querying portfolio projects.
 */
public interface ProjectService {

    /**
     * Retrieves all featured projects for the homepage.
     * 
     * @return List of ProjectResponseDTOs
     */
    List<ProjectResponseDTO> getFeaturedProjects();

    /**
     * Retrieves a single project by ID.
     * 
     * @param id Project ID
     * @return ProjectResponseDTO
     */
    ProjectResponseDTO getProjectById(Long id);
}
