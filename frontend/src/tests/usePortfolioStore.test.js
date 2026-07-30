import { describe, it, expect, beforeEach } from 'vitest';
import { usePortfolioStore } from '../store/usePortfolioStore';

describe('Zustand Portfolio Store', () => {
  beforeEach(() => {
    usePortfolioStore.setState({
      activeSection: 'hero',
      isChatOpen: false,
      selectedProject: null,
    });
  });

  it('should initialize with default state', () => {
    const state = usePortfolioStore.getState();
    expect(state.activeSection).toBe('hero');
    expect(state.isChatOpen).toBe(false);
    expect(state.selectedProject).toBeNull();
  });

  it('should toggle chat drawer state', () => {
    const { toggleChat } = usePortfolioStore.getState();
    toggleChat();
    expect(usePortfolioStore.getState().isChatOpen).toBe(true);
    toggleChat();
    expect(usePortfolioStore.getState().isChatOpen).toBe(false);
  });

  it('should set active section correctly', () => {
    const { setActiveSection } = usePortfolioStore.getState();
    setActiveSection('projects');
    expect(usePortfolioStore.getState().activeSection).toBe('projects');
  });
});
