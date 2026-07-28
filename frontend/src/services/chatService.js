import api from './api';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description API service module for interacting with Spring AI Gemini Chatbot.
 */
export const chatService = {
  /**
   * Sends user chat prompt to Spring AI backend.
   * @param {string} message User chat prompt
   * @param {string} sessionId Optional conversation session ID
   * @returns {Promise<Object>} { reply: string }
   */
  sendMessage: async (message, sessionId = null) => {
    const response = await api.post('/chat', { message, sessionId });
    return response.data;
  },
};
