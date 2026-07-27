import React from 'react';
import { UIConstants } from '../../constants/UIConstants';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Experience timeline section.
 */
export const ExperienceSection = () => {
  return (
    <section id="experience" className="py-20 bg-slate-900/50 relative">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        
        {/* Section Header */}
        <div className="text-center max-w-3xl mx-auto mb-16">
          <h2 className="section-title">{UIConstants.EXPERIENCE.TITLE}</h2>
          <p className="section-subtitle">{UIConstants.EXPERIENCE.SUBTITLE}</p>
        </div>

        {/* Timeline Items */}
        <div className="max-w-3xl mx-auto space-y-8 relative before:absolute before:inset-0 before:left-4 md:before:left-1/2 before:-ml-px before:w-0.5 before:bg-slate-800">
          {UIConstants.EXPERIENCE.TIMELINE.map((item, index) => (
            <div key={index} className="relative flex items-center justify-between md:justify-normal md:odd:flex-row-reverse group">
              
              {/* Timeline Marker Node */}
              <div className="flex items-center justify-center w-9 h-9 rounded-full bg-slate-900 border-2 border-cyan-500 text-cyan-400 font-bold text-xs shadow shrink-0 md:order-1 md:group-odd:-translate-x-1/2 md:group-even:translate-x-1/2">
                ⚡
              </div>

              {/* Card Container */}
              <div className="w-[calc(100%-3rem)] md:w-[calc(50%-2.5rem)] glass-panel p-6 border border-slate-800 hover:border-cyan-500/50 transition-colors">
                <div className="flex items-center justify-between gap-2 mb-2">
                  <h3 className="font-bold text-white text-base">{item.role}</h3>
                  <span className="text-xs font-semibold px-2.5 py-0.5 rounded-full bg-cyan-950/80 text-cyan-400 border border-cyan-800">
                    {item.period}
                  </span>
                </div>
                <div className="text-xs font-medium text-slate-400 mb-4">{item.company} • {item.location}</div>
                <ul className="space-y-2 text-xs text-slate-300 list-disc list-inside leading-relaxed">
                  {item.points.map((pt, i) => (
                    <li key={i}>{pt}</li>
                  ))}
                </ul>
              </div>

            </div>
          ))}
        </div>

      </div>
    </section>
  );
};
