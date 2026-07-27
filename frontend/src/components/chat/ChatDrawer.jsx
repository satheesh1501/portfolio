import React, { useState, useRef, useEffect } from 'react';
import { usePortfolioStore } from '../../store/usePortfolioStore';
import { chatService } from '../../services/chatService';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 6.0.0
 * 
 * @description Impressive Recruiter AI Drawer featuring single initial welcome message and starter chips.
 */
export const ChatDrawer = () => {
  const { isChatOpen, toggleChat, chatMessages, addChatMessage } = usePortfolioStore();
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);
  const messagesEndRef = useRef(null);

  useEffect(() => {
    if (isChatOpen) messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [chatMessages, isChatOpen]);

  const handleSend = async (promptText = null) => {
    const text = promptText || input;
    if (!text.trim() || loading) return;

    addChatMessage({ sender: 'user', text });
    if (!promptText) setInput('');
    setLoading(true);

    try {
      const data = await chatService.sendMessage(text);
      addChatMessage({ sender: 'bot', text: data.reply });
    } catch (err) {
      addChatMessage({ sender: 'bot', text: err.message || 'AI Assistant is currently unavailable.' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {/* FLOATING AI BUTTON IN BOTTOM RIGHT */}
      <button className="chat-widget-button" onClick={toggleChat} aria-label="Ask AI Assistant">
        <span className="sparkle">✦</span> Ask AI
      </button>

      {/* CHAT PANEL WHEN OPEN */}
      {isChatOpen && (
        <div id="chatPanel" style={{ display: 'flex' }}>
          <div className="chat-header">
            <div className="bot-icon">🤖</div>
            <h5>Ask About Satheesh</h5>
            <button className="chat-close" onClick={toggleChat}>✕</button>
          </div>

          <div className="chat-body">
            {/* RECRUITER & VISITOR STARTER CHIPS */}
            <div className="suggested-row" style={{ marginBottom: '12px' }}>
              <span className="suggested-chip" onClick={() => handleSend('Education & Degree')}>🎓 Education & Degree</span>
              <span className="suggested-chip" onClick={() => handleSend('LeetCode Milestones')}>🧩 LeetCode Milestones (138+)</span>
              <span className="suggested-chip" onClick={() => handleSend('Target Role & Work Setup')}>🎯 Target Role & Work Setup</span>
              <span className="suggested-chip" onClick={() => handleSend('Why Hire Satheesh?')}>⚡ Why Hire Satheesh?</span>
            </div>

            {/* SINGLE SOURCE OF TRUTH CONVERSATION MESSAGES */}
            {chatMessages.map((msg, idx) => (
              <div key={idx} className={msg.sender === 'user' ? 'user-bubble' : 'ai-bubble'}>
                {msg.text}
              </div>
            ))}
            {loading && <div className="ai-bubble">AI is typing...</div>}
            <div ref={messagesEndRef} />
          </div>

          <form onSubmit={(e) => { e.preventDefault(); handleSend(); }} className="chat-input-row">
            <input
              type="text"
              placeholder="Ask anything about Satheesh..."
              value={input}
              onChange={(e) => setInput(e.target.value)}
            />
            <button type="submit" className="chat-send" disabled={!input.trim() || loading}>➤</button>
          </form>
        </div>
      )}
    </>
  );
};
