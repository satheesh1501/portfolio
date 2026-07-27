import api from './api';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description API service module for resume download tracking and statistics.
 */
export const resumeService = {
  /**
   * Tracks a resume download event on the backend.
   * @returns {Promise<Object>} Response status
   */
  trackDownload: async () => {
    const response = await api.post('/resume/download');
    return response.data;
  },

  /**
   * Fetches resume download analytics.
   * @returns {Promise<Object>} { totalDownloads, last30DaysDownloads }
   */
  getStats: async () => {
    const response = await api.get('/resume/stats');
    return response.data;
  },
};
