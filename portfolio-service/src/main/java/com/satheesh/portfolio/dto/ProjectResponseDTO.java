package com.satheesh.portfolio.dto;

import java.util.List;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Immutable response DTO representing software project showcase details.
 * Abstracts internal database columns like display order and audit timestamps from the REST payload.
 *
 * @param id Unique project ID
 * @param title Project title/name
 * @param description Detailed project architectural summary
 * @param techStack Array of technologies used in the project
 * @param githubUrl Target GitHub source code repository link
 * @param liveUrl Live application production deployment URL
 * @param caseStudyUrl Link to technical design breakdown or architecture document
 * @param featured Flag indicating whether project should be highlighted in featured showcase
 * @param status Project development status string representation (e.g., ACTIVE, IN_PROGRESS)
 */
public record ProjectResponseDTO(
        Long id,
        String title,
        String description,
        List<String> techStack,
        String githubUrl,
        String liveUrl,
        String caseStudyUrl,
        Boolean featured,
        String status
) {}
