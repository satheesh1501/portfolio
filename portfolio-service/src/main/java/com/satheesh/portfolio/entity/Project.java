package com.satheesh.portfolio.entity;

import com.satheesh.portfolio.enums.ProjectStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

/**
 * JPA entity for portfolio projects.
 * Maps to the 'projects' table created by V3 Flyway migration.
 *
 * tech_stack uses JSONB (via @JdbcTypeCode) — Hibernate 6 native approach.
 * Maps cleanly to List<String> in Java without any custom converter.
 */
@Entity
@Table(name = "projects")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    /**
     * Tech stack stored as JSONB in PostgreSQL.
     * Example DB value: ["Java 21","Spring Boot 3.2","React 18"]
     * Hibernate 6 maps this directly to List<String> — no custom converter needed.
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "tech_stack", columnDefinition = "jsonb")
    private List<String> techStack;

    @Column(name = "github_url", length = 500)
    private String githubUrl;

    @Column(name = "live_url", length = 500)
    private String liveUrl;

    @Column(name = "case_study_url", length = 500)
    private String caseStudyUrl;

    @Column(nullable = false)
    @Builder.Default
    private Boolean featured = false;

    /** Controls display ordering on the portfolio page. Lower = appears first. */
    @Column(name = "display_order", nullable = false)
    @Builder.Default
    private Integer displayOrder = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ProjectStatus status = ProjectStatus.ACTIVE;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
