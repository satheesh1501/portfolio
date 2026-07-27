package com.satheesh.portfolio.dto;

import java.util.List;

/**
 * Immutable response DTO for project data returned to the frontend.
 *
 * Intentionally hides internal entity fields from the API contract:
 *  - displayOrder (internal ordering column, not relevant to consumers)
 *  - createdAt    (internal audit field)
 *
 * status is exposed as a String (e.g. "ACTIVE", "IN_PROGRESS") so the
 * frontend can render a badge without needing knowledge of the Java enum.
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
