import React from 'react';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 3.1.0
 * 
 * @description ProjectsSection with Recruiter-focused categorized tech stacks.
 */
export const ProjectsSection = () => {
  const civilProject = {
    id: 2,
    title: "Civil Platform — Construction SaaS",
    status: "IN_PROGRESS",
    description: "A multi-tenant SaaS platform for construction companies to digitize site workforce management, automated geo-fenced attendance, payroll calculation, and real-time site reporting.",
    techStack: [
      "Java 21", "Spring Boot 3", "Spring Security",
      "React 18",
      "PostgreSQL", "Redis", "Flyway",
      "AWS App Runner"
    ],
  };

  const portfolioProject = {
    id: 1,
    title: "Developer Portfolio Microservices",
    status: "ACTIVE",
    description: "A full-stack microservices portfolio featuring Spring Boot 3, Spring AI Gemini chatbot, Apache Kafka event-driven email notifications, Redis rate limiting & caching, Testcontainers integration testing, and automated AWS cloud deployment via GitHub Actions.",
    techStack: [
      "Java 21", "Spring Boot 3", "Spring AI Gemini",
      "Apache Kafka", "Redis",
      "JUnit 5", "Mockito", "Testcontainers", "Cypress E2E",
      "GitHub Actions", "AWS S3/CloudFront"
    ],
    githubUrl: "https://github.com/satheesh1501/portfolio",
  };

  return (
    <section className="projects-section" id="projects">
      <div className="projects-wrap">
        <span className="eyebrow">Selected work</span>
        <h2 className="projects-title">Projects</h2>
        <p className="projects-sub">Production-grade systems designed and built end-to-end.</p>

        {/* CARD 1 — Civil Platform */}
        <div className="project-card amber-accent">
          <div className="badge-row">
            <span className="status-badge amber">
              <span className="dot"></span> In Progress
            </span>
            <span className="repo-badge">🔒 Private Repository</span>
          </div>

          <h3 className="project-title">{civilProject.title}</h3>
          <p className="project-desc">{civilProject.description}</p>

          <div className="tech-chip-row">
            {civilProject.techStack.map((tech) => (
              <span key={tech} className="tech-badge-item">{tech}</span>
            ))}
          </div>
        </div>

        {/* CARD 2 — Developer Portfolio */}
        <div className="project-card cyan-accent">
          <div className="badge-row">
            <span className="status-badge cyan">
              <span className="dot"></span> Live System
            </span>
            <span className="repo-badge">🌐 Open Source</span>
          </div>

          <h3 className="project-title">{portfolioProject.title}</h3>
          <p className="project-desc">{portfolioProject.description}</p>

          <div className="tech-chip-row">
            {portfolioProject.techStack.map((tech) => (
              <span key={tech} className="tech-badge-item">{tech}</span>
            ))}
          </div>

          <div className="project-actions">
            <a href={portfolioProject.githubUrl} target="_blank" rel="noopener noreferrer" className="btn btn-outline">
              GitHub Repository ↗
            </a>
          </div>
        </div>

      </div>
    </section>
  );
};
