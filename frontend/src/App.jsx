import React from 'react';
import { Navbar } from './components/layout/Navbar';
import { HeroSection } from './components/sections/HeroSection';
import { AboutSection } from './components/sections/AboutSection';
import { SkillsSection } from './components/sections/SkillsSection';
import { ExperienceSection } from './components/sections/ExperienceSection';
import { ProjectsSection } from './components/sections/ProjectsSection';
import { ContactSection } from './components/sections/ContactSection';
import { Footer } from './components/layout/Footer';
import { ChatWidget } from './components/chat/ChatWidget';
import { ChatDrawer } from './components/chat/ChatDrawer';
import { ProjectModal } from './components/ui/ProjectModal';
import { Toast } from './components/ui/Toast';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Main application composition component.
 */
function App() {
  return (
    <div className="min-h-screen bg-slate-950 text-slate-100 selection:bg-cyan-500 selection:text-white">
      {/* Toast Alert */}
      <Toast />

      {/* Sticky Glassmorphism Header */}
      <Navbar />

      {/* Main Page Content */}
      <main>
        <HeroSection />
        <AboutSection />
        <SkillsSection />
        <ExperienceSection />
        <ProjectsSection />
        <ContactSection />
      </main>

      {/* Developer Footer */}
      <Footer />

      {/* Interactive AI Chatbot Widget & Drawer */}
      <ChatWidget />
      <ChatDrawer />

      {/* Case Study Modal */}
      <ProjectModal />
    </div>
  );
}

export default App;
