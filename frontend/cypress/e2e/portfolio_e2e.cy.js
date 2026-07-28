describe('Portfolio Full-Stack Master E2E Automated Test Suite', () => {

  beforeEach(() => {
    cy.visit('/');
  });

  // ==========================================
  // CATEGORY 1: HERO SECTION & RESUME DOWNLOAD
  // ==========================================
  describe('Hero Section & Social Navigation', () => {
    it('TC-HERO-01 [Positive]: Should display correct header quote and developer name', () => {
      cy.get('.name').should('contain.text', 'Satheesh Kumar P');
      cy.get('.nav-quote').should('contain.text', 'Full stack developer by profession, product builder by passion');
    });

    it('TC-HERO-02 [Positive]: Should verify LeetCode, GitHub, and LinkedIn social profile links', () => {
      cy.get('a[aria-label="LeetCode"]')
        .should('have.attr', 'href', 'https://leetcode.com/u/satheeshkumar1501/')
        .should('have.attr', 'target', '_blank');

      cy.get('a[aria-label="GitHub"]')
        .should('have.attr', 'href', 'https://github.com/satheesh1501')
        .should('have.attr', 'target', '_blank');

      cy.get('a[aria-label="LinkedIn"]')
        .should('have.attr', 'href', 'https://www.linkedin.com/in/satheesh-kumar-p-24ab5427b/')
        .should('have.attr', 'target', '_blank');
    });

    it('TC-HERO-03 [Positive]: Should trigger Resume Download API and alert user', () => {
      cy.contains('button', 'Download Resume ⬇').click();
      cy.get('.toast-container', { timeout: 10000 }).should('be.visible').and('contain.text', 'Resume download started!');
    });
  });

  // ==========================================
  // CATEGORY 2: CONTACT FORM & RATE LIMITING
  // ==========================================
  describe('Contact Form Pipeline & Validation', () => {
    it('TC-CONTACT-01 [Negative]: Should show in-line error messages when submitting empty form', () => {
      cy.get('#contact').scrollIntoView();
      cy.contains('button', 'Send Message →').click();

      cy.get('.error-text').should('have.length.at.least', 3);
      cy.contains('Name is required.').should('be.visible');
      cy.contains('Email is required.').should('be.visible');
      cy.contains('Subject is required.').should('be.visible');
    });

    it('TC-CONTACT-02 [Negative]: Should reject invalid email format (missing @)', () => {
      cy.get('#contact').scrollIntoView();
      cy.get('input[placeholder="Your Name"]').type('John Doe');
      cy.get('input[placeholder="Your Email"]').type('invalid-email-format');
      cy.get('input[placeholder="Subject"]').type('Hiring Inquiry');
      cy.get('textarea[placeholder*="Your Message"]').type('Hello Satheesh, we would like to discuss an engineering role.');

      cy.contains('button', 'Send Message →').click();
      cy.contains('Please enter a valid email address').should('be.visible');
    });

    it('TC-CONTACT-03 [Negative]: Should reject invalid special characters in subject', () => {
      cy.get('#contact').scrollIntoView();
      cy.get('input[placeholder="Your Name"]').type('Test User');
      cy.get('input[placeholder="Your Email"]').type('test@example.com');
      cy.get('input[placeholder="Subject"]').type('<script>alert("xss")</script>');
      cy.get('textarea[placeholder*="Your Message"]').type('Testing malicious payload injection prevention.');

      cy.contains('button', 'Send Message →').click();
      cy.contains('Subject contains invalid special characters').should('be.visible');
    });

    it('TC-CONTACT-04 [Positive]: Submit valid contact message', () => {
      const uniqueId = Math.floor(100000 + Math.random() * 900000);

      cy.get('#contact').scrollIntoView();
      cy.get('input[placeholder="Your Name"]').type(`Recruiter ${uniqueId}`);
      cy.get('input[placeholder="Your Email"]').type(`recruiter_${uniqueId}@company.com`);
      cy.get('input[placeholder="Subject"]').type('Senior Full Stack Developer Opportunity');
      cy.get('textarea[placeholder*="Your Message"]').type('Hi Satheesh, we were impressed by your Java 21 microservices portfolio and would love to connect!');

      cy.contains('button', 'Send Message →').click();
      cy.get('.toast-container', { timeout: 15000 }).should('be.visible');
    });
  });

  // ==========================================
  // CATEGORY 3: AI CHATBOT ENGINE & NLP PROMPTS
  // ==========================================
  describe('AI Chatbot Engine & Boundary Tests', () => {
    it('TC-AI-01 [Positive]: Should open drawer with single welcome message and starter chips', () => {
      cy.get('.chat-widget-button').click();
      cy.get('#chatPanel').should('be.visible');

      cy.get('.ai-bubble').should('have.length', 1)
        .and('contain.text', "Hi! I'm Satheesh's AI Portfolio Assistant");

      cy.get('.suggested-chip').should('have.length', 4);
    });

    it('TC-AI-02 [Positive]: Should handle LeetCode starter chip click', () => {
      cy.get('.chat-widget-button').click();
      cy.contains('.suggested-chip', 'LeetCode Milestones').click();

      cy.get('.user-bubble').should('contain.text', 'LeetCode Milestones');
      cy.get('.ai-bubble').last().should('contain.text', '138+ Data Structures & Algorithms problems');
    });

    it('TC-AI-03 [Positive]: Should resolve NLP query "his skills"', () => {
      cy.get('.chat-widget-button').click();
      cy.get('input[placeholder*="Ask anything"]').type('his skills{enter}');

      cy.get('.user-bubble').should('contain.text', 'his skills');
      cy.get('.ai-bubble').last().should('contain.text', 'Java 21, Spring Boot 3, Spring Security');
    });

    it('TC-AI-04 [Positive]: Should resolve NLP query "what company he looking for"', () => {
      cy.get('.chat-widget-button').click();
      cy.get('input[placeholder*="Ask anything"]').type('what company he looking for{enter}');

      cy.get('.user-bubble').should('contain.text', 'what company he looking for');
      cy.get('.ai-bubble').last().should('contain.text', 'Senior / Mid-Level Full Stack Developer');
    });

    it('TC-AI-05 [Negative]: Should strictly reject off-topic query "what is the weather in London"', () => {
      cy.get('.chat-widget-button').click();
      cy.get('input[placeholder*="Ask anything"]').type('what is the weather in London{enter}');

      cy.get('.user-bubble').should('contain.text', 'what is the weather in London');
      cy.get('.ai-bubble').last().should('contain.text', 'Please ask a valid question about Satheesh Kumar P!');
    });
  });

});
