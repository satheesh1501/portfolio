import React from 'react';
import { usePortfolioStore } from '../../store/usePortfolioStore';

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Global notification toast component.
 */
export const Toast = () => {
  const { toast, hideToast } = usePortfolioStore();

  if (!toast) return null;

  const isSuccess = toast.type === 'success';

  return (
    <div className="fixed top-20 right-4 z-50 animate-bounce">
      <div
        className={`px-4 py-3 rounded-xl border shadow-2xl flex items-center gap-3 text-xs font-semibold ${
          isSuccess
            ? 'bg-emerald-950/90 border-emerald-500/80 text-emerald-200'
            : 'bg-red-950/90 border-red-500/80 text-red-200'
        }`}
      >
        <span>{isSuccess ? '✅' : '⚠️'}</span>
        <span>{toast.message}</span>
        <button onClick={hideToast} className="ml-2 text-slate-400 hover:text-white">
          ✕
        </button>
      </div>
    </div>
  );
};
