import React from 'react';
import { UIConstants } from '../../constants/UIConstants';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Developer footer displaying copyright, social links, and live status.
 */
export const Footer = () => {
  return (
    <footer className="bg-slate-950 border-t border-slate-800/80 py-12 text-slate-400 text-sm">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col md:flex-row items-center justify-between gap-6">
        
        {/* Brand Copyright */}
        <div className="flex flex-col gap-1 text-center md:text-left">
          <span className="font-semibold text-slate-200">{UIConstants.BRAND.NAME}</span>
          <span className="text-xs text-slate-500">{UIConstants.FOOTER.COPYRIGHT}</span>
        </div>

        {/* Operational Microservices Status Badge */}
        <div className="flex items-center gap-2 bg-slate-900/90 border border-slate-800 px-4 py-2 rounded-full text-xs">
          <span className="w-2 h-2 rounded-full bg-emerald-400 animate-pulse"></span>
          <span className="text-slate-300 font-medium">{UIConstants.FOOTER.STATUS_LIVE}</span>
          <span className="text-slate-600">|</span>
          <span className="text-slate-400">{UIConstants.FOOTER.STATUS_BACKEND}</span>
        </div>

        {/* Social Links */}
        <div className="flex items-center gap-4">
          <a
            href={UIConstants.BRAND.GITHUB_URL}
            target="_blank"
            rel="noopener noreferrer"
            className="hover:text-cyan-400 transition-colors p-2 rounded-lg bg-slate-900 border border-slate-800"
            aria-label="GitHub Profile"
          >
            GitHub
          </a>
          <a
            href={UIConstants.BRAND.LINKEDIN_URL}
            target="_blank"
            rel="noopener noreferrer"
            className="hover:text-cyan-400 transition-colors p-2 rounded-lg bg-slate-900 border border-slate-800"
            aria-label="LinkedIn Profile"
          >
            LinkedIn
          </a>
        </div>

      </div>
    </footer>
  );
};
