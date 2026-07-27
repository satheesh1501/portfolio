import React from 'react';
import { UIConstants } from '../../constants/UIConstants';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Categorized technical skills grid.
 */
export const SkillsSection = () => {
  return (
    <section id="skills" className="py-20 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Section Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <h2 className="section-title">{UIConstants.SKILLS.TITLE}</h2>
          <p className="section-subtitle">{UIConstants.SKILLS.SUBTITLE}</p>
        </div>

        {/* Skills Categories Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {UIConstants.SKILLS.CATEGORIES.map((category) => (
            <div key={category.name} className="glass-panel p-6 border border-slate-800 flex flex-col">
              <h3 className="text-lg font-bold text-cyan-400 mb-4 pb-2 border-b border-slate-800">
                {category.name}
              </h3>
              <div className="flex flex-wrap gap-2">
                {category.items.map((item) => (
                  <span key={item} className="badge-tech bg-slate-900/80">
                    {item}
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
