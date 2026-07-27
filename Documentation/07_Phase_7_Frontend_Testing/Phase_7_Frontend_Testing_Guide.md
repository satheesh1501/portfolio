# Phase 7 — Frontend Testing (Vitest & Cypress E2E Automation) Guide

## Overview
This document covers the complete **Phase 7 Frontend Testing Plan** for the Portfolio Single Page Application, combining **Vitest** for fast unit and state testing with **Cypress** for end-to-end real browser testing and Mochawesome HTML report generation.

---

## 1. Test Architecture & Pyramids

```
           / \
          /   \     End-to-End (E2E) Browser Tests
         / E2E \    Cypress (User Flows & UI Interactions)
        /-------\
       / Compon. \  Component Unit & State Tests
      /   Tests   \ Vitest (Zustand Store & Validation Rules)
     /-------------\
```

---

## 2. Vitest Unit & State Test Suite (`9 / 9 PASSED`)

### File Locations
- Validation Rules: `frontend/src/tests/validation.test.js`
- Global State Store: `frontend/src/tests/store.test.js`

### Executed Test Matrix
| Test File | Scenario | Type | Description |
| :--- | :--- | :--- | :--- |
| `validation.test.js` | `TC-VAL-01` | **Positive** | Passes clean contact form data |
| `validation.test.js` | `TC-VAL-02` | **Negative** | Catches missing required fields (Name, Email, Subject, Message) |
| `validation.test.js` | `TC-VAL-03` | **Negative** | Rejects invalid email formatting (missing `@`) |
| `validation.test.js` | `TC-VAL-04` | **Negative** | Rejects `<script>` XSS malicious inputs |
| `validation.test.js` | `TC-VAL-05` | **Negative** | Rejects short messages under 10 characters |
| `store.test.js` | `TC-STORE-01` | **Positive** | Updates active section navigation state |
| `store.test.js` | `TC-STORE-02` | **Positive** | Toggles AI Chat drawer open/closed state |
| `store.test.js` | `TC-STORE-03` | **Positive** | Appends user and bot messages to chat history |
| `store.test.js` | `TC-STORE-04` | **Positive** | Handles toast notification display and auto-clear |

---

## 3. Cypress E2E Automated Browser Suite (`cypress/e2e/portfolio_e2e.cy.js`)

### Executed E2E Matrix
1. **Hero & Social Icons**: Verifies developer headline, quote, and **LeetCode**, **GitHub**, **LinkedIn** social links.
2. **Resume Download API**: Triggers `/api/resume/download` and verifies PDF file download.
3. **Contact & Microservices**: Validates empty form errors, invalid email syntax, invalid subject special characters, valid message submission, and **Redis 2-minute rate-limit warning toast**.
4. **AI Chatbot Engine**: Verifies single welcome message, starter chips, NLP queries (`"his skills"`, `"what company he looking for"`), and **off-topic prompt rejection**.

---

## 4. Execution Commands & HTML Reports

### Vitest Unit Tests
```bash
npx vitest run
```

### Cypress Interactive Live Browser UI
```bash
npx cypress open
```

### Cypress Headless + HTML Execution Report
```bash
npm run cypress:run
```
*(Report saved at `frontend/cypress/reports/index.html`)*
