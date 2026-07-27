import React from 'react';
import { usePortfolioStore } from '../../store/usePortfolioStore';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 2.1.0
 * 
 * @description Global notification toast component with fallback Vanilla CSS styles and toast-container class for test assertions.
 */
export const Toast = () => {
  const { toast, hideToast } = usePortfolioStore();

  if (!toast) return null;

  const isSuccess = toast.type === 'success';
  const isWarning = toast.type === 'warning';

  return (
    <div
      className="toast-container"
      style={{
        position: 'fixed',
        top: '90px',
        right: '28px',
        zIndex: 9999,
        animation: 'slideInRight 0.3s ease-out forwards',
      }}
    >
      <div
        style={{
          padding: '14px 20px',
          borderRadius: '12px',
          display: 'flex',
          alignItems: 'center',
          gap: '12px',
          fontSize: '13.5px',
          fontWeight: '500',
          fontFamily: "'Inter', sans-serif",
          boxShadow: '0 12px 36px rgba(0,0,0,0.5), 0 0 20px rgba(0,212,255,0.2)',
          backdropFilter: 'blur(16px)',
          WebkitBackdropFilter: 'blur(16px)',
          border: isSuccess
            ? '1px solid rgba(0, 212, 255, 0.6)'
            : isWarning
            ? '1px solid rgba(245, 166, 35, 0.6)'
            : '1px solid rgba(255, 77, 77, 0.6)',
          background: isSuccess
            ? 'rgba(10, 25, 45, 0.95)'
            : isWarning
            ? 'rgba(35, 25, 10, 0.95)'
            : 'rgba(35, 10, 15, 0.95)',
          color: isSuccess ? '#00d4ff' : isWarning ? '#f5a623' : '#ff4d4d',
          maxWidth: '420px',
        }}
      >
        <span style={{ fontSize: '16px' }}>
          {isSuccess ? '✅' : isWarning ? '⏳' : '⚠️'}
        </span>
        <span style={{ flex: 1, lineHeight: '1.5' }}>{toast.message}</span>
        <button
          onClick={hideToast}
          style={{
            background: 'none',
            border: 'none',
            color: 'var(--slate-400)',
            cursor: 'pointer',
            fontSize: '16px',
            marginLeft: '8px',
            padding: '2px 4px',
          }}
        >
          ✕
        </button>
      </div>
    </div>
  );
};
