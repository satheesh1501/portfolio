import axios from 'axios';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Centralized Axios HTTP client instance.
 * Automatically injects X-Trace-Id header for MDC log correlation with backend.
 */
const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 10000,
});

// Request Interceptor: Attach TraceID header
api.interceptors.request.use((config) => {
  const traceId = 'fe-' + Math.random().toString(36).substring(2, 10);
  config.headers['X-Trace-Id'] = traceId;
  return config;
});

// Response Interceptor: Format errors consistently
api.interceptors.response.use(
  (response) => response,
  (error) => {
    const errorPayload = {
      message: error.response?.data?.error || 'An unexpected error occurred.',
      status: error.response?.status || 500,
      fieldErrors: error.response?.data?.fieldErrors || null,
    };
    return Promise.reject(errorPayload);
  }
);

export default api;
