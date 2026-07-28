import React from 'react';
import { usePortfolioStore } from '../../store/usePortfolioStore';
import { UIConstants } from '../../constants/UIConstants';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Case Study & Architecture modal for selected project.
 */
export const ProjectModal = () => {
  const { selectedProject, setSelectedProject } = usePortfolioStore();

  if (!selectedProject) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4 bg-slate-950/80 backdrop-blur-sm animate-fadeIn">
      <div className="glass-panel w-full max-w-2xl border border-slate-700 p-6 sm:p-8 max-h-[85vh] overflow-y-auto relative shadow-2xl">
        
        {/* Close Button */}
        <button
          onClick={() => setSelectedProject(null)}
          className="absolute top-4 right-4 text-slate-400 hover:text-white p-2 rounded-lg bg-slate-800/80 border border-slate-700"
          aria-label="Close Modal"
        >
          ✕
        </button>

        {/* Modal Content */}
        <div className="mb-4">
          <span
            className={`text-xs font-bold px-3 py-1 rounded-full uppercase tracking-wider ${
              selectedProject.status === 'ACTIVE'
                ? 'bg-emerald-950/80 text-emerald-400 border border-emerald-800'
                : 'bg-amber-950/80 text-amber-400 border border-amber-800'
            }`}
          >
            {selectedProject.status === 'ACTIVE' ? UIConstants.PROJECTS.BADGE_ACTIVE : UIConstants.PROJECTS.BADGE_IN_PROGRESS}
          </span>
        </div>

        <h3 className="text-2xl font-bold text-white mb-3">{selectedProject.title}</h3>
        <p className="text-slate-300 text-sm leading-relaxed mb-6">{selectedProject.description}</p>

        {/* System Architecture Highlights */}
        <div className="bg-slate-950/60 p-4 rounded-xl border border-slate-800 mb-6">
          <h4 className="text-xs font-bold text-cyan-400 uppercase tracking-wider mb-3">Key Architectural Highlights</h4>
          <ul className="space-y-2 text-xs text-slate-300 list-disc list-inside">
            {selectedProject.status === 'ACTIVE' ? (
              <>
                <li>Event-driven microservices architecture using Apache Kafka for async notifications.</li>
                <li>Spring Boot 3 REST API with Redis sliding-window rate limiting (ZSET).</li>
                <li>PostgreSQL 16 Flyway schema migrations & JSONB tech stack storage.</li>
                <li>Integrated Spring AI Gemini Chatbot for contextual portfolio assistant.</li>
              </>
            ) : (
              <>
                <li>Enterprise SaaS multi-tenant construction project management platform.</li>
                <li>Role-Based Access Control (RBAC) and document management workflows.</li>
                <li>Real-time project timeline tracking and budget analytics engine.</li>
                <li>Currently in active development — Architecture Case Study available.</li>
              </>
            )}
          </ul>
        </div>

        {/* Tech Stack */}
        <div className="mb-6">
          <h4 className="text-xs font-bold text-slate-400 uppercase tracking-wider mb-2">Technologies Used</h4>
          <div className="flex flex-wrap gap-2">
            {selectedProject.techStack?.map((tech) => (
              <span key={tech} className="badge-tech">
                {tech}
              </span>
            ))}
          </div>
        </div>

        {/* Actions */}
        <div className="flex items-center justify-end gap-3 pt-4 border-t border-slate-800">
          {selectedProject.githubUrl && (
            <a href={selectedProject.githubUrl} target="_blank" rel="noopener noreferrer" className="btn-secondary text-xs">
              GitHub Repository
            </a>
          )}
          <button onClick={() => setSelectedProject(null)} className="btn-primary text-xs">
            Close Case Study
          </button>
        </div>

      </div>
    </div>
  );
};
