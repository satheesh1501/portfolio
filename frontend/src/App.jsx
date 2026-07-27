import React from 'react';
import { Navbar } from './components/layout/Navbar';
import { HeroSection } from './components/sections/HeroSection';
import { AboutSection } from './components/sections/AboutSection';
import { SkillsSection } from './components/sections/SkillsSection';
import { ExperienceSection } from './components/sections/ExperienceSection';
import { ProjectsSection } from './components/sections/ProjectsSection';
import { InterestsSection } from './components/sections/InterestsSection';
import { ContactSection } from './components/sections/ContactSection';
import { ChatDrawer } from './components/chat/ChatDrawer';
import { Toast } from './components/ui/Toast';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 3.1.0
 * 
 * @description Main Single Page Application entry component (Footer removed for clean SPA design).
 */
export function App() {
  return (
    <div className="portfolio-app">
      {/* Scroll-Spy Navbar */}
      <Navbar />

      {/* Main Page Sections */}
      <main>
        <HeroSection />
        <AboutSection />
        <SkillsSection />
        <ExperienceSection />
        <ProjectsSection />
        <InterestsSection />
        <ContactSection />
      </main>

      {/* Floating AI Chatbot Drawer */}
      <ChatDrawer />

      {/* Global Toast Notification */}
      <Toast />
    </div>
  );
}

export default App;
