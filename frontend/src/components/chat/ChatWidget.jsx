import React from 'react';
import { usePortfolioStore } from '../../store/usePortfolioStore';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description ChatWidget matching WireFrame/contact-chatbot-footer.html 100%.
 */
export const ChatWidget = () => {
  const { toggleChat } = usePortfolioStore();

  return (
    <button className="chat-widget-button" onClick={toggleChat} aria-label="Ask AI Assistant">
      <span className="sparkle">✦</span>
      <span>Ask AI</span>
    </button>
  );
};
