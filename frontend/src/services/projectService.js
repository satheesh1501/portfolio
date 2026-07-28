import api from './api';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description API service module for portfolio project queries.
 */
export const projectService = {
  /**
   * Fetches featured active and in-progress portfolio projects.
   * @returns {Promise<Array>} List of ProjectResponseDTOs
   */
  getFeaturedProjects: async () => {
    const response = await api.get('/projects');
    return response.data;
  },

  /**
   * Fetches detailed information for a single project by ID.
   * @param {number} id Project ID
   * @returns {Promise<Object>} ProjectResponseDTO
   */
  getProjectById: async (id) => {
    const response = await api.get(`/projects/${id}`);
    return response.data;
  },
};
