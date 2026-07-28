package com.satheesh.portfolio.repository;

import com.satheesh.portfolio.entity.Project;
import com.satheesh.portfolio.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Spring Data JPA Repository interface for Project entity persistence operations.
 * Manages queries for active/featured software engineering project showcases with display ordering.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Retrieves featured projects with statuses in the specified list, ordered by displayOrder ascending.
     * Used on homepage showcases to load ACTIVE and IN_PROGRESS projects in custom sequence.
     *
     * @param statuses List of acceptable ProjectStatus values
     * @return List of matching Project entities
     */
    List<Project> findByFeaturedTrueAndStatusInOrderByDisplayOrderAsc(List<ProjectStatus> statuses);

    /**
     * Retrieves all projects matching a specific status, ordered by displayOrder ascending.
     *
     * @param status Target ProjectStatus filter constraint
     * @return List of matching Project entities
     */
    List<Project> findByStatusOrderByDisplayOrderAsc(ProjectStatus status);
}
