import { create } from 'zustand';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Centralized Zustand global state store.
 * Manages active navigation section, modal states, toast notifications,
 * AI Chat drawer state, and chat conversation history.
 */
export const usePortfolioStore = create((set) => ({
  // Active Navigation
  activeSection: 'about',
  setActiveSection: (section) => set({ activeSection: section }),

  // Selected Project Case Study Modal
  selectedProject: null,
  setSelectedProject: (project) => set({ selectedProject: project }),

  // AI Chatbot State
  isChatOpen: false,
  toggleChat: () => set((state) => ({ isChatOpen: !state.isChatOpen })),
  chatMessages: [
    { sender: 'bot', text: 'Hello! I am Satheesh\'s AI Assistant. Ask me anything about his experience, Java 21 microservices, projects, or tech stack!' },
  ],
  addChatMessage: (msg) => set((state) => ({ chatMessages: [...state.chatMessages, msg] })),

  // Toast Notification State
  toast: null,
  showToast: (message, type = 'info') => {
    set({ toast: { message, type } });
    setTimeout(() => set({ toast: null }), 4000);
  },
  hideToast: () => set({ toast: null }),
}));
