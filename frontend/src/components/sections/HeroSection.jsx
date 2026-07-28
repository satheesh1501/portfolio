import React from 'react';
import { usePortfolioStore } from '../../store/usePortfolioStore';
import { resumeService } from '../../services/resumeService';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 2.6.0
 * 
 * @description HeroSection with monochromatic harmonized social icons.
 */
export const HeroSection = () => {
  const { showToast } = usePortfolioStore();

  const handleDownloadResume = async () => {
    try {
      await resumeService.trackDownload();
      showToast('Resume download started!', 'success');
    } catch (err) {
      console.warn('Backend download tracking failed, proceeding with file download', err);
    }

    const link = document.createElement('a');
    link.href = '/Satheesh_Kumar_P_Resume.pdf';
    link.download = 'Satheesh_Kumar_P_Resume.pdf';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <section className="hero" id="hero">
      <div className="hero-grid">

        {/* LEFT COLUMN */}
        <div className="content-col">
          <span className="eyebrow">Full Stack Developer</span>
          <h1 className="name">Satheesh Kumar P</h1>
          <div className="underline"></div>

          <div className="typing-wrap">
            <span className="typing-container">Java 21 · Spring Boot · React · AWS ·</span>
          </div>

          <p className="sub-headline">
            I build high-performance microservices and clean web applications — turning enterprise requirements into production-ready software.
          </p>

          <div className="btn-row">
            <a href="#projects" className="btn btn-fill">View My Work →</a>
            <button onClick={handleDownloadResume} className="btn btn-outline">Download Resume ⬇</button>
          </div>

          <div className="social-row">
            <a className="social-icon" href="https://github.com/satheesh1501" target="_blank" rel="noopener noreferrer" aria-label="GitHub">
              <svg viewBox="0 0 24 24" fill="currentColor"><path d="M12 .5C5.65.5.5 5.65.5 12c0 5.09 3.29 9.4 7.86 10.93.57.1.78-.25.78-.55v-1.94c-3.2.7-3.87-1.54-3.87-1.54-.53-1.34-1.29-1.7-1.29-1.7-1.05-.72.08-.71.08-.71 1.17.08 1.78 1.2 1.78 1.2 1.03 1.77 2.7 1.26 3.36.97.1-.75.4-1.26.73-1.55-2.56-.29-5.25-1.28-5.25-5.7 0-1.26.45-2.29 1.19-3.1-.12-.29-.52-1.46.11-3.05 0 0 .97-.31 3.18 1.18a11.06 11.06 0 015.79 0c2.2-1.49 3.17-1.18 3.17-1.18.63 1.59.23 2.76.11 3.05.74.81 1.19 1.84 1.19 3.1 0 4.43-2.7 5.4-5.27 5.69.41.36.78 1.08.78 2.17v3.22c0 .3.21.66.79.55A10.51 10.51 0 0023.5 12C23.5 5.65 18.35.5 12 .5z"/></svg>
            </a>
            <a className="social-icon" href="https://www.linkedin.com/in/satheesh-kumar-p-24ab5427b/" target="_blank" rel="noopener noreferrer" aria-label="LinkedIn">
              <svg viewBox="0 0 24 24" fill="currentColor"><path d="M20.45 20.45h-3.56v-5.57c0-1.33-.02-3.03-1.85-3.03-1.85 0-2.14 1.45-2.14 2.94v5.66H9.34V9h3.42v1.56h.05c.48-.9 1.64-1.85 3.38-1.85 3.61 0 4.28 2.38 4.28 5.47v6.27zM5.34 7.43a2.07 2.07 0 110-4.13 2.07 2.07 0 010 4.13zM7.12 20.45H3.56V9h3.56v11.45z"/></svg>
            </a>
            <a className="social-icon" href="https://leetcode.com/u/satheeshkumar1501/" target="_blank" rel="noopener noreferrer" aria-label="LeetCode">
              <svg viewBox="0 0 24 24" fill="currentColor">
                <path d="M16.102 17.93l-2.697 2.607c-.466.45-1.08.7-1.724.7a2.45 2.45 0 0 1-1.724-.7l-5.04-4.87a2.482 2.482 0 0 1 0-3.527l5.04-4.87a2.45 2.45 0 0 1 1.724-.7c.644 0 1.258.25 1.724.7l2.697 2.607a.64.64 0 0 0 .9-.9l-2.697-2.607A3.73 3.73 0 0 0 11.68 5a3.73 3.73 0 0 0-2.628 1.07l-5.04 4.87a3.76 3.76 0 0 0 0 5.343l5.04 4.87A3.73 3.73 0 0 0 11.68 22a3.73 3.73 0 0 0 2.628-1.07l2.697-2.607a.64.64 0 0 0-.903-.893z"/>
                <path d="M10.8 12.8a.64.64 0 0 0 .9 0l6.7-6.5a.64.64 0 1 0-.9-.9l-6.7 6.5a.64.64 0 0 0 0 .9z"/>
                <path d="M17.4 12.8a.64.64 0 0 0 .9 0l3.8-3.7a.64.64 0 1 0-.9-.9l-3.8 3.7a.64.64 0 0 0 0 .9z"/>
              </svg>
            </a>
          </div>
        </div>

        {/* RIGHT COLUMN — PHOTO INSIDE FRAME */}
        <div className="visual-col">
          <div className="avatar-frame">
            <div className="avatar-photo-wrap">
              <img src="/profile.jpg" alt="Satheesh Kumar P" />
            </div>

            <div className="tech-badge badge-java">☕ Java 21</div>
            <div className="tech-badge badge-react">⚛ React 18</div>
            <div className="tech-badge badge-spring">🌿 Spring Boot 3</div>
            <div className="tech-badge badge-aws">☁ AWS</div>
          </div>
        </div>

      </div>
    </section>
  );
};
