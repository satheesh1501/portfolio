package com.satheesh.portfolio.mapper;

import com.satheesh.portfolio.dto.ContactRequestDTO;
import com.satheesh.portfolio.dto.ContactResponseDTO;
import com.satheesh.portfolio.entity.ContactMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper between ContactRequestDTO ↔ ContactMessage entity ↔ ContactResponseDTO.
 *
 * Why MapStruct?
 *  - Generated at compile time: zero reflection, zero runtime overhead.
 *  - Type-safe: missing or mismatched fields = compiler error, not runtime bug.
 *  - Clean separation: entity fields (ipAddress, status) are not exposed in the API.
 *
 * The generated implementation (ContactMapperImpl.java) is auto-created
 * under target/generated-sources during 'mvn compile'.
 */
@Mapper(componentModel = "spring")
public interface ContactMapper {

    /**
     * Converts incoming ContactRequestDTO to a ContactMessage entity for DB persistence.
     * Fields not present in the DTO (ipAddress, status, id, timestamps) are
     * set by the service layer or Hibernate defaults.
     */
    ContactMessage toEntity(ContactRequestDTO dto);

    /**
     * Converts a saved ContactMessage entity to a ContactResponseDTO for the frontend.
     * - 'message' field is set to a fixed success message (not the raw DB entity message).
     * - 'timestamp' maps from entity's createdAt field.
     */
    @Mapping(target = "message",
             constant = "Your message has been received. We will get back to you soon!")
    @Mapping(target = "timestamp", source = "createdAt")
    ContactResponseDTO toResponseDTO(ContactMessage entity);
}
