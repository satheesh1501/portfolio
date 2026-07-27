import React from 'react';
import { UIConstants } from '../../constants/UIConstants';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description About section highlighting engineering philosophy and background.
 */
export const AboutSection = () => {
  return (
    <section id="about-details" className="py-20 bg-slate-900/50 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Section Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <h2 className="section-title">{UIConstants.ABOUT.TITLE}</h2>
          <p className="section-subtitle">{UIConstants.ABOUT.SUBTITLE}</p>
        </div>

        {/* Content Grid */}
        <div className="grid grid-cols-1 lg:grid-cols-12 gap-12 items-center">
          
          {/* Left Text Narrative */}
          <div className="lg:col-span-7 space-y-6 text-slate-300 leading-relaxed">
            <p className="text-base sm:text-lg">
              {UIConstants.ABOUT.DESCRIPTION_PARAGRAPH_1}
            </p>
            <p className="text-base sm:text-lg">
              {UIConstants.ABOUT.DESCRIPTION_PARAGRAPH_2}
            </p>
          </div>

          {/* Right Highlights Cards */}
          <div className="lg:col-span-5 grid grid-cols-1 sm:grid-cols-2 gap-4">
            {UIConstants.ABOUT.HIGHLIGHTS.map((item) => (
              <div key={item.title} className="glass-panel p-5 border border-slate-800 hover:border-cyan-500/50 transition-colors">
                <h3 className="font-bold text-white mb-2 text-base">{item.title}</h3>
                <p className="text-xs text-slate-400 leading-relaxed">{item.desc}</p>
              </div>
            ))}
          </div>

        </div>

      </div>
    </section>
  );
};
