package com.satheesh.portfolio.repository;

import com.satheesh.portfolio.entity.Project;
import com.satheesh.portfolio.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for Project entities.
 *
 * Key queries:
 *  - findByFeaturedTrueAndStatusInOrderByDisplayOrderAsc:
 *      Used on the homepage to load featured ACTIVE + IN_PROGRESS projects,
 *      ordered by displayOrder ascending (lower number = shown first).
 *
 *  - findByStatusOrderByDisplayOrderAsc:
 *      Used for admin/filtered views of projects by status.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    /**
     * Returns featured projects with any of the given statuses, sorted by displayOrder.
     * Usage: findByFeaturedTrueAndStatusInOrderByDisplayOrderAsc(
     *            List.of(ProjectStatus.ACTIVE, ProjectStatus.IN_PROGRESS)
     *        )
     */
    List<Project> findByFeaturedTrueAndStatusInOrderByDisplayOrderAsc(List<ProjectStatus> statuses);

    /**
     * Returns all projects matching a given status, sorted by displayOrder.
     */
    List<Project> findByStatusOrderByDisplayOrderAsc(ProjectStatus status);
}
