import React from 'react';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 2.0.0
 * 
 * @description AboutSection matching WireFrame/about-section.html 100% with pulsing amber badge dot and hover chip glow.
 */
export const AboutSection = () => {
  return (
    <section className="about-section" id="about">
      <div className="about-grid">

        {/* LEFT COLUMN */}
        <div className="text-col">
          <span className="eyebrow">Get to know me</span>
          <h2 className="about-title">About Me</h2>
          <p className="about-text">
            Software Developer with 2.5 years of experience delivering enterprise
            applications using Java, Spring Boot, PostgreSQL, and React. Currently
            at HCL Technologies supporting global pharma client applications.
            Passionate about clean architecture, test automation, and building
            scalable SaaS solutions.
          </p>

          <div className="stat-row">
            <div className="stat-card">
              <div className="stat-num">2.5</div>
              <div className="stat-label">Years Exp</div>
            </div>
            <div className="stat-card">
              <div className="stat-num">15+</div>
              <div className="stat-label">Prod Bugs Fixed</div>
            </div>
            <div className="stat-card">
              <div className="stat-num">25%</div>
              <div className="stat-label">API Perf Gain</div>
            </div>
          </div>
        </div>

        {/* RIGHT COLUMN */}
        <div className="visual-col">
          <div className="featured-card">
            <span className="status-badge-amber">
              <span className="dot-amber"></span> In Progress
            </span>
            <h3 className="featured-title">Civil Platform — Construction SaaS</h3>
            <p className="featured-desc">
              A multi-tenant construction SaaS for workforce tracking, geo-fenced
              site attendance, payroll computation, and real-time site analytics
              — built as a full-stack product.
            </p>
            <div className="tech-badge-row">
              <span className="tech-badge-item">Java 21</span>
              <span className="tech-badge-item">Spring Boot 3</span>
              <span className="tech-badge-item">PostgreSQL</span>
              <span className="tech-badge-item">Redis</span>
              <span className="tech-badge-item">React</span>
              <span className="tech-badge-item">AWS</span>
            </div>
          </div>
        </div>

      </div>
    </section>
  );
};
