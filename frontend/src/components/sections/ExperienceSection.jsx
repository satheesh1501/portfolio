import React from 'react';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 2.1.0
 * 
 * @description ExperienceSection matching updated work experience requirements.
 */
export const ExperienceSection = () => {
  return (
    <section className="exp-section" id="experience">
      <div className="exp-wrap">
        <span className="eyebrow">Career path</span>
        <h2 className="exp-title">Work Experience</h2>

        <div className="timeline">

          {/* CARD 1 — HCL Technologies */}
          <div className="tl-item">
            <div className="tl-dot"></div>
            <div className="tl-card">
              <span className="date-pill">Mar 2025 – Present</span>
              <div className="tl-company">HCL Technologies</div>
              <div className="tl-role">Software Developer</div>
              <ul className="tl-bullets">
                <li>Developed and maintained Spring Boot backend services for a global pharma client application.</li>
                <li>Built automated Export APIs that reduced manual export turnaround time by 25%.</li>
                <li>Resolved 15+ critical cross-stack bugs across Angular, Spring Boot, and SQL databases.</li>
              </ul>
              <div className="tech-badge-row">
                <span className="tech-badge-item">Java</span>
                <span className="tech-badge-item">Spring Boot</span>
                <span className="tech-badge-item">Angular</span>
                <span className="tech-badge-item">MySQL</span>
              </div>
            </div>
          </div>

          {/* CARD 2 — Profit.co */}
          <div className="tl-item">
            <div className="tl-dot"></div>
            <div className="tl-card">
              <span className="date-pill">Apr 2024 – Mar 2025</span>
              <div className="tl-company">Profit.co</div>
              <div className="tl-role">Junior Software Engineer</div>
              <ul className="tl-bullets">
                <li>Designed and shipped core REST API modules for the OKR Management SaaS platform.</li>
                <li>Optimized database queries and API endpoints, improving response times by 20%.</li>
                <li>Implemented Elasticsearch full-text search for fast querying of large organizational datasets.</li>
              </ul>
              <div className="tech-badge-row">
                <span className="tech-badge-item">Java</span>
                <span className="tech-badge-item">Spring Boot</span>
                <span className="tech-badge-item">Angular</span>
                <span className="tech-badge-item">Elasticsearch</span>
              </div>
            </div>
          </div>

        </div>
      </div>
    </section>
  );
};
