import api from './api';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description API service module for submitting contact form messages.
 */
export const contactService = {
  /**
   * Submits contact message to backend REST API.
   * @param {Object} contactData { name, email, subject, message }
   * @returns {Promise<Object>} ContactResponseDTO
   */
  submitContact: async (contactData) => {
    const response = await api.post('/contact', contactData);
    return response.data;
  },
};
