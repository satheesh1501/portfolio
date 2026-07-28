# 📄 Phase 5 Documentation — React 18 Frontend UI & AI Chatbot Widget

## 📌 Executive Summary
Phase 5 constructed a high-performance, visually stunning React 18 Single Page Application (SPA) inside `frontend/`. Built with Vite, Zustand state management, Axios API client, and Tailwind CSS design system, it strictly adheres to enterprise frontend principles: zero hardcoded strings (`UIConstants.js`), modular CSS utility tokens without duplication (`index.css`), and full end-to-end integration with live Spring Boot backend microservices and Spring AI Gemini Chatbot.

---

## 🏗️ Architecture & Component Inventory

```
frontend/src/
├── constants/UIConstants.js               [Centralized UI Text & Labels]
├── services/
│   ├── api.js                             [Axios Instance + X-Trace-Id Header]
│   ├── projectService.js                  [GET /api/v1/projects]
│   ├── contactService.js                  [POST /api/v1/contact]
│   ├── resumeService.js                   [POST /api/v1/resume/download]
│   └── chatService.js                     [POST /api/v1/chat]
├── store/usePortfolioStore.js             [Zustand Global State Store]
├── components/
│   ├── layout/
│   │   ├── Navbar.jsx                     [Glassmorphism Sticky Header]
│   │   └── Footer.jsx                     [Developer Footer & Live Status Badge]
│   ├── sections/
│   │   ├── HeroSection.jsx                [Hero Intro, CTAs, Tech Badges]
│   │   ├── AboutSection.jsx               [Engineering Philosophy & Narrative]
│   │   ├── SkillsSection.jsx              [Categorized Tech Stack Grid]
│   │   ├── ExperienceSection.jsx          [Career Timeline]
│   │   ├── ProjectsSection.jsx            [Live Backend Project Showcase]
│   │   └── ContactSection.jsx             [Validated Form & Toast Feedback]
│   ├── chat/
│   │   ├── ChatWidget.jsx                 [Floating Bottom-Right Trigger Button]
│   │   └── ChatDrawer.jsx                 [Interactive AI Chat Assistant Drawer]
│   └── ui/
│       ├── ProjectModal.jsx               [Architecture Case Study Modal]
│       └── Toast.jsx                      [Global Alert Notification Toast]
└── index.css                              [Design System Utility Tokens]
```

### 1. Zero Hardcoded Strings (`UIConstants.js`)
All text, button labels, section headings, social URLs, form placeholders, quick prompts, and error responses are centralized.

### 2. Design System Tokens (`index.css`)
Custom CSS utility rules (`.glass-panel`, `.glass-nav`, `.btn-primary`, `.btn-secondary`, `.badge-tech`, `.section-title`) eliminate inline CSS duplication across components.

### 3. Live Microservices Integration
- **Projects Section**: Connects to `GET http://localhost:8080/api/v1/projects`. Displays active projects and `IN_PROGRESS` Civil Platform project with Case Study modal drawer.
- **Contact Section**: Connects to `POST http://localhost:8080/api/v1/contact`. Triggers 4-layer validation, toast alerts, and Kafka event -> Mailpit email notification (`http://localhost:8025`).
- **AI Chatbot Widget**: Floating trigger button opening conversation drawer interacting with Spring AI backend (`POST http://localhost:8080/api/v1/chat`).

---

## 🧪 Build & Production Verification
- `npm run build` executed successfully:
  - `dist/index.html` (1.00 kB)
  - `dist/assets/index.css` (2.05 kB)
  - `dist/assets/index.js` (223.68 kB)
- Zero syntax, import, or JSX compilation errors.
