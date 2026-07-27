import React, { useState, useEffect } from 'react';
import { UIConstants } from '../../constants/UIConstants';
import { usePortfolioStore } from '../../store/usePortfolioStore';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Glassmorphism sticky navbar with active section highlight and mobile menu.
 */
export const Navbar = () => {
  const [scrolled, setScrolled] = useState(false);
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const { activeSection, setActiveSection } = usePortfolioStore();

  useEffect(() => {
    const handleScroll = () => {
      setScrolled(window.scrollY > 20);
    };
    window.addEventListener('scroll', handleScroll);
    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  return (
    <header className={`fixed top-0 left-0 right-0 z-50 transition-all duration-300 ${scrolled ? 'glass-nav py-3 shadow-lg' : 'bg-transparent py-5'}`}>
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex items-center justify-between">
        
        {/* Brand Logo */}
        <a href="#about" className="flex items-center gap-2 group" onClick={() => setActiveSection('about')}>
          <div className="w-10 h-10 rounded-xl bg-gradient-to-tr from-cyan-500 to-indigo-600 flex items-center justify-center font-bold text-white shadow-md group-hover:scale-105 transition-transform">
            SK
          </div>
          <span className="font-bold text-lg tracking-tight text-white group-hover:text-cyan-400 transition-colors">
            {UIConstants.BRAND.NAME}
          </span>
        </a>

        {/* Desktop Navigation Links */}
        <nav className="hidden md:flex items-center gap-1">
          {UIConstants.NAV_LINKS.map((link) => {
            const sectionId = link.href.replace('#', '');
            const isActive = activeSection === sectionId;
            return (
              <a
                key={link.label}
                href={link.href}
                onClick={() => setActiveSection(sectionId)}
                className={`px-4 py-2 rounded-lg text-sm font-medium transition-all ${
                  isActive
                    ? 'bg-slate-800/80 text-cyan-400 border border-slate-700/80 shadow-sm'
                    : 'text-slate-300 hover:text-white hover:bg-slate-800/40'
                }`}
              >
                {link.label}
              </a>
            );
          })}
        </nav>

        {/* Contact CTA Button */}
        <div className="hidden md:block">
          <a href="#contact" className="btn-primary text-xs py-2 px-4">
            {UIConstants.HERO.SECONDARY_CTA}
          </a>
        </div>

        {/* Mobile Hamburger Button */}
        <button
          onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          className="md:hidden text-slate-300 hover:text-white p-2 rounded-lg bg-slate-800/60 border border-slate-700"
          aria-label="Toggle Navigation Menu"
        >
          <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            {mobileMenuOpen ? (
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            ) : (
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
            )}
          </svg>
        </button>
      </div>

      {/* Mobile Menu Dropdown */}
      {mobileMenuOpen && (
        <div className="md:hidden glass-panel mx-4 mt-2 p-4 shadow-xl border border-slate-700">
          <div className="flex flex-col gap-2">
            {UIConstants.NAV_LINKS.map((link) => (
              <a
                key={link.label}
                href={link.href}
                onClick={() => {
                  setActiveSection(link.href.replace('#', ''));
                  setMobileMenuOpen(false);
                }}
                className="px-4 py-2 rounded-lg text-sm font-medium text-slate-200 hover:bg-slate-800 hover:text-cyan-400"
              >
                {link.label}
              </a>
            ))}
          </div>
        </div>
      )}
    </header>
  );
};
