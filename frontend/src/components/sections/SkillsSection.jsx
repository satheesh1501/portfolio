import React, { useState } from 'react';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 2.1.0
 * 
 * @description SkillsSection matching WireFrame/skills-section.html 100% where chips glow cyan on hover.
 */
export const SkillsSection = () => {
  const [selectedChips, setSelectedChips] = useState([]);

  const toggleChip = (chipName) => {
    setSelectedChips((prev) =>
      prev.includes(chipName)
        ? prev.filter((c) => c !== chipName)
        : [...prev, chipName]
    );
  };

  const isSelected = (chipName) => selectedChips.includes(chipName);

  const categories = [
    {
      title: "Backend",
      icon: "⚙",
      skills: ["Java 21", "Spring Boot 3.x", "Spring Security", "Spring Cache", "Spring AI", "Spring Retry", "Lombok", "MapStruct", "REST APIs"],
    },
    {
      title: "Frontend",
      icon: "◧",
      skills: ["React 18", "Vite", "React Query", "Zustand", "Framer Motion", "Axios", "HTML5/CSS3", "Tailwind CSS"],
    },
    {
      title: "Database & Cache",
      icon: "⛁",
      skills: ["PostgreSQL", "Redis (Upstash)", "Flyway DB", "Full-Text Search", "MySQL"],
    },
    {
      title: "Messaging & AI",
      icon: "⚡",
      skills: ["Apache Kafka", "Consumer Groups", "Gemini 1.5 Flash", "SSE Streaming"],
    },
    {
      title: "Testing",
      icon: "✓",
      skills: ["JUnit 5", "Mockito", "Testcontainers", "Cypress E2E", "Vitest", "React Testing Library"],
    },
    {
      title: "DevOps & Cloud",
      icon: "☁",
      skills: ["Docker", "GitHub Actions", "AWS S3", "CloudFront", "App Runner", "ECR", "SonarCloud", "CodeQL", "Gitleaks"],
    },
  ];

  return (
    <section className="skills-section" id="skills">
      <div className="skills-wrap">
        <span className="eyebrow">Toolbox</span>
        <h2 className="skills-title">Technical Skills</h2>
        <p className="skills-sub">The languages, frameworks, and tools I use to design, build, and ship production software.</p>

        <div className="skills-grid">
          {categories.map((cat) => (
            <div key={cat.title} className="skill-card">
              <div className="skill-card-head">
                <div className="skill-icon">{cat.icon}</div>
                <h3>{cat.title}</h3>
              </div>
              <div className="chip-row">
                {cat.skills.map((skill) => (
                  <span
                    key={skill}
                    onClick={() => toggleChip(skill)}
                    className={`chip ${isSelected(skill) ? 'chip-active' : ''}`}
                  >
                    {skill}
                  </span>
                ))}
              </div>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
};
