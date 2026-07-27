import React from 'react';
import { usePortfolioStore } from '../../store/usePortfolioStore';
import { UIConstants } from '../../constants/UIConstants';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Floating AI Chatbot trigger button at fixed bottom-right position.
 */
export const ChatWidget = () => {
  const { toggleChat, isChatOpen } = usePortfolioStore();

  return (
    <div className="fixed bottom-6 right-6 z-40">
      <button
        onClick={toggleChat}
        className="w-14 h-14 rounded-full bg-gradient-to-tr from-cyan-500 to-indigo-600 text-white flex items-center justify-center shadow-2xl hover:scale-110 active:scale-95 transition-all duration-300 relative group border border-cyan-300/30"
        aria-label="Open AI Assistant Chat"
      >
        {/* Glow Ring */}
        <span className="absolute -inset-1 rounded-full bg-cyan-500/30 animate-pulse pointer-events-none"></span>

        {isChatOpen ? (
          <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
          </svg>
        ) : (
          <span className="text-2xl">🤖</span>
        )}
      </button>
    </div>
  );
};
