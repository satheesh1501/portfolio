import { create } from 'zustand';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 2.0.0
 * 
 * @description Centralized Zustand global state store.
 * Manages active navigation section, modal states, toast notifications,
 * AI Chat drawer state, and single initial chat conversation history.
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
    { sender: 'bot', text: '👋 Hi! I\'m Satheesh\'s AI Portfolio Assistant. Ask me anything about his B.Tech IT degree at Mepco Schlenk (7.56 CGPA), 138+ LeetCode solved, 2.5+ yrs exp at Profit.co & HCL, or work setup preferences!' },
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
