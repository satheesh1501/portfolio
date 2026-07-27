import React from 'react';
import { UIConstants } from '../../constants/UIConstants';
import { resumeService } from '../../services/resumeService';
import { usePortfolioStore } from '../../store/usePortfolioStore';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Hero introduction section with interactive CTAs and tech stack badges.
 */
export const HeroSection = () => {
  const { showToast } = usePortfolioStore();

  const handleResumeDownload = async () => {
    try {
      await resumeService.trackDownload();
      showToast('Resume download tracked!', 'success');
    } catch (e) {
      // Ignore background tracking errors
    }
  };

  return (
    <section id="about" className="relative pt-32 pb-20 md:pt-40 md:pb-28 overflow-hidden">
      
      {/* Background Decorative Radial Gradient Glow */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] bg-gradient-to-tr from-cyan-500/10 to-indigo-500/10 rounded-full blur-3xl pointer-events-none"></div>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 relative z-10">
        <div className="max-w-3xl">
          
          {/* Availability Status Badge */}
          <div className="inline-flex items-center gap-2 px-3 py-1.5 rounded-full bg-cyan-950/60 border border-cyan-800/60 text-cyan-400 text-xs font-semibold tracking-wider mb-6">
            <span className="w-2 h-2 rounded-full bg-cyan-400 animate-ping"></span>
            {UIConstants.HERO.BADGE}
          </div>

          {/* Main Hero Name & Title */}
          <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold tracking-tight text-white mb-4">
            Hi, I'm <span className="bg-gradient-to-r from-cyan-400 to-indigo-400 bg-clip-text text-transparent">{UIConstants.BRAND.NAME}</span>
          </h1>

          <h2 className="text-xl sm:text-2xl font-semibold text-slate-300 mb-6">
            {UIConstants.BRAND.TITLE}
          </h2>

          <p className="text-base sm:text-lg text-slate-400 leading-relaxed mb-8">
            {UIConstants.HERO.TAGLINE || UIConstants.BRAND.TAGLINE}
          </p>

          {/* Action CTAs */}
          <div className="flex flex-wrap items-center gap-4 mb-12">
            <a href="#projects" className="btn-primary">
              {UIConstants.HERO.PRIMARY_CTA}
            </a>
            
            <a href="#contact" className="btn-secondary">
              {UIConstants.HERO.SECONDARY_CTA}
            </a>

            <a
              href="/resume.pdf"
              download="Satheesh_Kumar_P_Resume.pdf"
              onClick={handleResumeDownload}
              className="btn-secondary text-slate-300 hover:text-cyan-400"
            >
              📄 {UIConstants.HERO.RESUME_CTA}
            </a>
          </div>

          {/* Quick Tech Badges */}
          <div className="flex flex-wrap items-center gap-2 pt-6 border-t border-slate-800/80">
            <span className="text-xs font-semibold text-slate-500 uppercase tracking-wider mr-2">Core Tech:</span>
            {["Java 21", "Spring Boot 3", "Apache Kafka", "Redis", "PostgreSQL", "Docker", "AWS", "React 18"].map((tech) => (
              <span key={tech} className="badge-tech">
                {tech}
              </span>
            ))}
          </div>

        </div>
      </div>
    </section>
  );
};
