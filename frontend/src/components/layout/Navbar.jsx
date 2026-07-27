import React, { useState, useEffect } from 'react';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 3.4.0
 * 
 * @description Navbar with custom header quote: "Full stack developer by profession, product builder by passion"
 */
export const Navbar = () => {
  const [activeSection, setActiveSection] = useState('hero');

  useEffect(() => {
    const sections = document.querySelectorAll('section[id]');
    
    const handleScroll = () => {
      const scrollPosition = window.scrollY + 180;

      sections.forEach((section) => {
        const top = section.offsetTop;
        const height = section.offsetHeight;
        const id = section.getAttribute('id');

        if (scrollPosition >= top && scrollPosition < top + height) {
          setActiveSection(id);
        }
      });
    };

    window.addEventListener('scroll', handleScroll);
    handleScroll();

    return () => window.removeEventListener('scroll', handleScroll);
  }, []);

  const navItems = [
    { id: 'hero', label: 'Home' },
    { id: 'about', label: 'About' },
    { id: 'skills', label: 'Skills' },
    { id: 'experience', label: 'Experience' },
    { id: 'projects', label: 'Projects' },
    { id: 'interests', label: 'Interests' },
    { id: 'contact', label: 'Contact' },
  ];

  return (
    <header className="navbar-header">
      {/* LEFT SIDE: CUSTOM HEADER QUOTE */}
      <div className="nav-quote">
        <span className="quote-dot">⚡</span>
        <span>Full stack developer by profession, product builder by passion</span>
      </div>

      {/* RIGHT SIDE: NAVIGATION LINKS */}
      <nav className="nav-links">
        {navItems.map((item) => (
          <a
            key={item.id}
            href={`#${item.id}`}
            className={`nav-link ${activeSection === item.id ? 'active' : ''}`}
          >
            {item.label}
          </a>
        ))}
      </nav>
    </header>
  );
};
