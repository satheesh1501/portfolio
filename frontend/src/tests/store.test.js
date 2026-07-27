import { describe, it, expect, beforeEach } from 'vitest';
import { usePortfolioStore } from '../store/usePortfolioStore';

describe('Zustand Global Store & State Management Tests', () => {

  beforeEach(() => {
    usePortfolioStore.setState({
      activeSection: 'about',
      isChatOpen: false,
      toast: null,
      chatMessages: [
        { sender: 'bot', text: 'Welcome!' }
      ]
    });
  });

  it('TC-STORE-01 [Positive]: Should update active section when requested', () => {
    const { setActiveSection } = usePortfolioStore.getState();
    setActiveSection('skills');
    expect(usePortfolioStore.getState().activeSection).toBe('skills');
  });

  it('TC-STORE-02 [Positive]: Should toggle AI Chat drawer open and closed', () => {
    const { toggleChat } = usePortfolioStore.getState();
    expect(usePortfolioStore.getState().isChatOpen).toBe(false);

    toggleChat();
    expect(usePortfolioStore.getState().isChatOpen).toBe(true);

    toggleChat();
    expect(usePortfolioStore.getState().isChatOpen).toBe(false);
  });

  it('TC-STORE-03 [Positive]: Should append new chat messages to history', () => {
    const { addChatMessage } = usePortfolioStore.getState();
    addChatMessage({ sender: 'user', text: 'his skills' });

    const messages = usePortfolioStore.getState().chatMessages;
    expect(messages).toHaveLength(2);
    expect(messages[1].text).toBe('his skills');
  });

  it('TC-STORE-04 [Positive]: Should set and clear toast notifications', () => {
    const { showToast, hideToast } = usePortfolioStore.getState();
    showToast('Message sent!', 'success');

    expect(usePortfolioStore.getState().toast).toEqual({
      message: 'Message sent!',
      type: 'success'
    });

    hideToast();
    expect(usePortfolioStore.getState().toast).toBeNull();
  });
});
