import React, { useState, useRef, useEffect } from 'react';
import { usePortfolioStore } from '../../store/usePortfolioStore';
import { UIConstants } from '../../constants/UIConstants';
import { chatService } from '../../services/chatService';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Interactive AI Chatbot drawer connected to POST /api/v1/chat.
 */
export const ChatDrawer = () => {
  const { isChatOpen, toggleChat, chatMessages, addChatMessage } = usePortfolioStore();
  const [input, setInput] = useState('');
  const [loading, setLoading] = useState(false);
  const messagesEndRef = useRef(null);

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    if (isChatOpen) {
      scrollToBottom();
    }
  }, [chatMessages, isChatOpen]);

  if (!isChatOpen) return null;

  const handleSend = async (textToSend = null) => {
    const query = textToSend || input;
    if (!query.trim() || loading) return;

    addChatMessage({ sender: 'user', text: query });
    if (!textToSend) setInput('');
    setLoading(true);

    try {
      const data = await chatService.sendMessage(query);
      addChatMessage({ sender: 'bot', text: data.reply });
    } catch (err) {
      addChatMessage({ sender: 'bot', text: err.message || 'Sorry, AI assistant is temporary unavailable.' });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="fixed bottom-24 right-4 sm:right-6 z-50 w-[calc(100vw-2rem)] sm:w-96 glass-panel border border-slate-700 shadow-2xl overflow-hidden flex flex-col h-[520px]">
      
      {/* Header */}
      <div className="bg-gradient-to-r from-slate-900 via-slate-900 to-indigo-950 px-4 py-3.5 border-b border-slate-800 flex items-center justify-between">
        <div className="flex items-center gap-3">
          <div className="w-8 h-8 rounded-full bg-cyan-500/20 text-cyan-400 flex items-center justify-center text-lg font-bold">
            🤖
          </div>
          <div>
            <h3 className="font-bold text-white text-sm leading-tight">{UIConstants.CHATBOT.DRAWER_TITLE}</h3>
            <p className="text-[10px] text-cyan-400">{UIConstants.CHATBOT.DRAWER_SUBTITLE}</p>
          </div>
        </div>

        <button onClick={toggleChat} className="text-slate-400 hover:text-white p-1">
          ✕
        </button>
      </div>

      {/* Messages Container */}
      <div className="flex-1 p-4 overflow-y-auto space-y-3 bg-slate-950/40">
        {chatMessages.map((msg, idx) => (
          <div key={idx} className={`flex ${msg.sender === 'user' ? 'justify-end' : 'justify-start'}`}>
            <div
              className={`max-w-[82%] px-3.5 py-2.5 rounded-2xl text-xs leading-relaxed ${
                msg.sender === 'user'
                  ? 'bg-gradient-to-r from-cyan-600 to-blue-600 text-white rounded-br-none shadow'
                  : 'bg-slate-800/90 text-slate-200 border border-slate-700 rounded-bl-none'
              }`}
            >
              {msg.text}
            </div>
          </div>
        ))}

        {loading && (
          <div className="flex justify-start">
            <div className="bg-slate-800/90 border border-slate-700 rounded-2xl rounded-bl-none px-4 py-2.5 text-xs text-slate-400 flex items-center gap-1.5">
              <span className="w-1.5 h-1.5 bg-cyan-400 rounded-full animate-bounce"></span>
              <span className="w-1.5 h-1.5 bg-cyan-400 rounded-full animate-bounce [animation-delay:0.2s]"></span>
              <span className="w-1.5 h-1.5 bg-cyan-400 rounded-full animate-bounce [animation-delay:0.4s]"></span>
            </div>
          </div>
        )}
        <div ref={messagesEndRef} />
      </div>

      {/* Quick Prompts */}
      <div className="px-3 py-2 bg-slate-900/90 border-t border-slate-800/80 flex items-center gap-1.5 overflow-x-auto no-scrollbar">
        {UIConstants.CHATBOT.QUICK_PROMPTS.map((prompt, idx) => (
          <button
            key={idx}
            onClick={() => handleSend(prompt)}
            className="text-[10px] font-medium text-slate-300 bg-slate-800 hover:bg-slate-700 hover:text-cyan-300 border border-slate-700 px-2.5 py-1 rounded-full whitespace-nowrap shrink-0 transition-colors"
          >
            {prompt}
          </button>
        ))}
      </div>

      {/* Input Box */}
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleSend();
        }}
        className="p-3 bg-slate-900 border-t border-slate-800 flex items-center gap-2"
      >
        <input
          type="text"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          placeholder={UIConstants.CHATBOT.INPUT_PLACEHOLDER}
          className="flex-1 bg-slate-950 border border-slate-700 rounded-xl px-3.5 py-2 text-xs text-white placeholder-slate-500 focus:outline-none focus:border-cyan-500"
        />
        <button
          type="submit"
          disabled={!input.trim() || loading}
          className="btn-primary text-xs py-2 px-3 disabled:opacity-50"
        >
          {UIConstants.CHATBOT.SEND_BTN}
        </button>
      </form>

    </div>
  );
};
