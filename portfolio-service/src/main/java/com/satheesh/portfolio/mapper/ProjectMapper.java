package com.satheesh.portfolio.mapper;

import com.satheesh.portfolio.dto.ProjectResponseDTO;
import com.satheesh.portfolio.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * MapStruct mapper between Project entity ↔ ProjectResponseDTO.
 *
 * Key responsibility:
 *  - Hides internal DB fields from the API contract:
 *      displayOrder → excluded (internal ordering, not for consumers)
 *      createdAt    → excluded (internal audit field)
 *  - Converts ProjectStatus enum to String for frontend consumption.
 *
 * The generated implementation (ProjectMapperImpl.java) is auto-created
 * under target/generated-sources during 'mvn compile'.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper {

    /**
     * Maps a single Project entity to a ProjectResponseDTO.
     * status is converted from enum to its name string (e.g. "ACTIVE", "IN_PROGRESS").
     */
    @Mapping(target = "status", expression = "java(project.getStatus().name())")
    ProjectResponseDTO toResponseDTO(Project project);

    /**
     * Maps a list of Project entities to a list of ProjectResponseDTOs.
     * MapStruct auto-delegates each element to toResponseDTO().
     */
    List<ProjectResponseDTO> toResponseDTOList(List<Project> projects);
}
