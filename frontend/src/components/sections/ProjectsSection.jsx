import React, { useState, useEffect } from 'react';
import { UIConstants } from '../../constants/UIConstants';
import { projectService } from '../../services/projectService';
import { usePortfolioStore } from '../../store/usePortfolioStore';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Dynamic Project Showcase section connected to GET /api/v1/projects.
 * Displays Portfolio Microservices Platform and Civil Platform with live status badges.
 */
export const ProjectsSection = () => {
  const [projects, setProjects] = useState([]);
  const [loading, setLoading] = useState(true);
  const { setSelectedProject } = usePortfolioStore();

  useEffect(() => {
    const fetchProjects = async () => {
      try {
        const data = await projectService.getFeaturedProjects();
        setProjects(data);
      } catch (err) {
        // Fallback default projects if backend offline
        setProjects([
          {
            id: 1,
            title: "Portfolio Microservices Platform",
            description: "Production-grade developer portfolio built with event-driven microservices. Features Spring AI Gemini chatbot, Redis caching, Kafka event streaming, and AWS cloud deployment.",
            techStack: ["Java 21", "Spring Boot 3.2", "Apache Kafka", "Redis", "PostgreSQL", "React 18", "Docker", "AWS"],
            githubUrl: "https://github.com/satheesh1501/portfolio",
            status: "ACTIVE",
            featured: true,
          },
          {
            id: 2,
            title: "Civil Platform",
            description: "Enterprise SaaS platform for construction project management. Multi-tenant architecture with role-based access control, document management, and real-time project tracking. Private repo — architecture available via Case Study.",
            techStack: ["Java", "Spring Boot", "React", "PostgreSQL", "Redis", "Docker", "Microservices"],
            githubUrl: null,
            status: "IN_PROGRESS",
            featured: true,
          },
        ]);
      } finally {
        setLoading(false);
      }
    };

    fetchProjects();
  }, []);

  return (
    <section id="projects" className="py-20 bg-slate-900/40 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Section Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <h2 className="section-title">{UIConstants.PROJECTS.TITLE}</h2>
          <p className="section-subtitle">{UIConstants.PROJECTS.SUBTITLE}</p>
        </div>

        {/* Loading State */}
        {loading ? (
          <div className="flex justify-center items-center py-20">
            <div className="w-10 h-10 border-4 border-cyan-500 border-t-transparent rounded-full animate-spin"></div>
          </div>
        ) : (
          /* Projects Grid */
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            {projects.map((project) => (
              <div key={project.id} className="glass-panel p-6 sm:p-8 flex flex-col justify-between border border-slate-800 hover:border-cyan-500/50 transition-all duration-300">
                
                <div>
                  {/* Status Badge */}
                  <div className="flex items-center justify-between mb-4">
                    <span
                      className={`text-xs font-bold px-3 py-1 rounded-full uppercase tracking-wider ${
                        project.status === 'ACTIVE'
                          ? 'bg-emerald-950/80 text-emerald-400 border border-emerald-800'
                          : 'bg-amber-950/80 text-amber-400 border border-amber-800'
                      }`}
                    >
                      {project.status === 'ACTIVE' ? UIConstants.PROJECTS.BADGE_ACTIVE : UIConstants.PROJECTS.BADGE_IN_PROGRESS}
                    </span>
                  </div>

                  {/* Title & Description */}
                  <h3 className="text-xl font-bold text-white mb-3">{project.title}</h3>
                  <p className="text-slate-400 text-sm leading-relaxed mb-6">{project.description}</p>

                  {/* JSONB Tech Stack Tags */}
                  <div className="flex flex-wrap gap-2 mb-8">
                    {project.techStack?.map((tech) => (
                      <span key={tech} className="badge-tech">
                        {tech}
                      </span>
                    ))}
                  </div>
                </div>

                {/* Actions */}
                <div className="flex flex-wrap items-center gap-3 pt-4 border-t border-slate-800/80">
                  {project.githubUrl && (
                    <a
                      href={project.githubUrl}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="btn-secondary text-xs py-2 px-3"
                    >
                      🐙 {UIConstants.PROJECTS.GITHUB_BTN}
                    </a>
                  )}

                  <button
                    onClick={() => setSelectedProject(project)}
                    className="btn-primary text-xs py-2 px-3"
                  >
                    📖 {UIConstants.PROJECTS.CASE_STUDY_BTN}
                  </button>
                </div>

              </div>
            ))}
          </div>
        )}

      </div>
    </section>
  );
};
