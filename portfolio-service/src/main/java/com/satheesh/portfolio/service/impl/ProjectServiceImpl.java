package com.satheesh.portfolio.service.impl;

import com.satheesh.common.constants.AppConstants;
import com.satheesh.common.constants.MessageConstants;
import com.satheesh.portfolio.dto.ProjectResponseDTO;
import com.satheesh.portfolio.entity.Project;
import com.satheesh.portfolio.enums.ProjectStatus;
import com.satheesh.portfolio.exception.ResourceNotFoundException;
import com.satheesh.portfolio.mapper.ProjectMapper;
import com.satheesh.portfolio.repository.ProjectRepository;
import com.satheesh.portfolio.service.ProjectService;
import com.satheesh.common.util.AppLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Service implementation for querying portfolio projects.
 * Uses Redis caching (@Cacheable) to serve fast response times.
 */
@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);
    private static final String CLASS_NAME = ProjectServiceImpl.class.getSimpleName();

    private final ProjectRepository repository;
    private final ProjectMapper mapper;

    @Override
    @Cacheable(value = AppConstants.CACHE_FEATURED_PROJECTS, key = "'featured'")
    public List<ProjectResponseDTO> getFeaturedProjects() {
        String methodName = "getFeaturedProjects";
        AppLogger.info(log, "Portfolio-Service", CLASS_NAME, methodName, AppConstants.UNKNOWN_IP, MessageConstants.LOG_ACTION_GET_PROJECTS,
                "Fetching featured projects from PostgreSQL database (Cache Miss)");

        List<ProjectStatus> statuses = List.of(ProjectStatus.ACTIVE, ProjectStatus.IN_PROGRESS);
        List<Project> projects = repository.findByFeaturedTrueAndStatusInOrderByDisplayOrderAsc(statuses);
        return mapper.toResponseDTOList(projects);
    }

    @Override
    @Cacheable(value = AppConstants.CACHE_PROJECTS, key = "#id")
    public ProjectResponseDTO getProjectById(Long id) {
        String methodName = "getProjectById";
        AppLogger.info(log, "Portfolio-Service", CLASS_NAME, methodName, AppConstants.UNKNOWN_IP, MessageConstants.LOG_ACTION_GET_PROJECT_BY_ID,
                "Fetching project by ID: " + id);

        Project project = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.RESOURCE_NOT_FOUND + " ID: " + id));

        return mapper.toResponseDTO(project);
    }
}
