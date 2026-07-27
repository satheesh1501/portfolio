/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Centralized UI text, labels, API endpoints, navigation items,
 * social links, and message constants for the frontend application.
 * Prevents hardcoded strings in React components.
 */
export const UIConstants = {
  BRAND: {
    NAME: "Satheesh Kumar P",
    TITLE: "Senior Software Engineer — Microservices & Cloud Architect",
    TAGLINE: "Building High-Throughput Event-Driven Microservices, Cloud Native Applications & Enterprise SaaS Platforms",
    LOCATION: "Bengaluru, India",
    EMAIL: "psatheesh1501@gmail.com",
    GITHUB_URL: "https://github.com/satheesh1501",
    LINKEDIN_URL: "https://linkedin.com/in/satheesh-kumar-p",
    EXPERIENCE_YEARS: "5+ Years",
  },

  NAV_LINKS: [
    { label: "About", href: "#about" },
    { label: "Skills", href: "#skills" },
    { label: "Experience", href: "#experience" },
    { label: "Projects", href: "#projects" },
    { label: "Contact", href: "#contact" },
  ],

  HERO: {
    BADGE: "AVAILABLE FOR SENIOR BACKEND / ARCHITECT ROLES",
    PRIMARY_CTA: "View Projects",
    SECONDARY_CTA: "Contact Me",
    RESUME_CTA: "Download Resume",
    SUMMARY: "Specializing in Java 21, Spring Boot 3, Apache Kafka, Redis, PostgreSQL, Docker, AWS, and modern React 18 frontends.",
  },

  ABOUT: {
    TITLE: "About Me",
    SUBTITLE: "Passionate about building scalable distributed systems with clean architecture",
    DESCRIPTION_PARAGRAPH_1: "Experienced Senior Software Engineer with a strong background in architecting high-availability, low-latency microservice ecosystems using Java 21, Spring Boot, and event-driven architectures with Apache Kafka.",
    DESCRIPTION_PARAGRAPH_2: "Proficient in designing robust REST APIs, implementing Redis rate limiting, managing PostgreSQL database migrations via Flyway, and orchestrating containerized deployments with Docker and AWS.",
    HIGHLIGHTS: [
      { title: "Event-Driven Microservices", desc: "Decoupled systems using Apache Kafka & Redis" },
      { title: "Clean Code & Security", desc: "SOLID principles, OWASP compliance, XSS validation" },
      { title: "Cloud & DevOps", desc: "Docker Compose, GitHub Actions CI/CD, AWS deployment" },
      { title: "Full Stack Precision", desc: "Spring Boot 3 backend paired with React 18 frontend" },
    ],
  },

  SKILLS: {
    TITLE: "Technical Expertise",
    SUBTITLE: "Core technologies, frameworks, and engineering tools",
    CATEGORIES: [
      {
        name: "Backend Engineering",
        items: ["Java 21", "Spring Boot 3", "Spring Security", "Spring Data JPA", "Flyway SQL", "Hibernate 6", "RESTful APIs", "JWT Security"],
      },
      {
        name: "Event Streaming & Cache",
        items: ["Apache Kafka", "Redis Caching", "Redis ZSET Rate Limiting", "Zookeeper", "Spring AI Gemini"],
      },
      {
        name: "Database & Storage",
        items: ["PostgreSQL 16", "JSONB Document Types", "Redis 7", "Database Migration", "Query Optimization"],
      },
      {
        name: "Frontend & Cloud DevOps",
        items: ["React 18", "Vite", "Zustand State", "Axios", "Tailwind CSS", "Docker", "Docker Compose", "GitHub Actions CI/CD", "AWS EC2"],
      },
    ],
  },

  EXPERIENCE: {
    TITLE: "Professional Experience",
    SUBTITLE: "Track record of delivering enterprise-grade software solutions",
    TIMELINE: [
      {
        role: "Senior Software Engineer",
        company: "Enterprise Software SaaS",
        period: "2022 — Present",
        location: "Bengaluru, India",
        points: [
          "Architected event-driven microservices serving high-throughput requests with Java 21 & Spring Boot 3.",
          "Designed multi-tenant PostgreSQL databases and implemented Flyway SQL schema migrations.",
          "Integrated Apache Kafka event streaming and Redis sliding-window rate limiting for security compliance.",
          "Mentored engineering team members and established GitHub Actions CI/CD deployment pipelines.",
        ],
      },
      {
        role: "Software Engineer",
        company: "Tech Solutions Inc.",
        period: "2020 — 2022",
        location: "India",
        points: [
          "Developed core REST APIs and integrated third-party payment gateways & notification services.",
          "Optimized PostgreSQL database queries, reducing response times by 40%.",
          "Built responsive web user interfaces using React and modern CSS design systems.",
        ],
      },
    ],
  },

  PROJECTS: {
    TITLE: "Featured Projects",
    SUBTITLE: "Live demonstrations of production microservices and enterprise applications",
    GITHUB_BTN: "Source Code",
    LIVE_BTN: "Live Demo",
    CASE_STUDY_BTN: "Case Study & Architecture",
    BADGE_ACTIVE: "ACTIVE",
    BADGE_IN_PROGRESS: "IN PROGRESS",
  },

  CONTACT: {
    TITLE: "Get In Touch",
    SUBTITLE: "Have a project in mind or interested in collaborating? Send a message!",
    FORM_NAME_LABEL: "Your Name",
    FORM_NAME_PLACEHOLDER: "Satheesh Kumar",
    FORM_EMAIL_LABEL: "Email Address",
    FORM_EMAIL_PLACEHOLDER: "satheesh@example.com",
    FORM_SUBJECT_LABEL: "Subject",
    FORM_SUBJECT_PLACEHOLDER: "Opportunity / Project Inquiry",
    FORM_MESSAGE_LABEL: "Message",
    FORM_MESSAGE_PLACEHOLDER: "Write your message here...",
    SUBMIT_BTN: "Send Message",
    SUBMITTING_BTN: "Sending Message...",
    SUCCESS_TITLE: "Message Sent!",
    SUCCESS_DESC: "Thank you for reaching out. Your message has been received!",
    ERROR_TITLE: "Submission Failed",
  },

  CHATBOT: {
    TRIGGER_TOOLTIP: "Chat with AI Assistant",
    DRAWER_TITLE: "Satheesh's AI Portfolio Assistant",
    DRAWER_SUBTITLE: "Powered by Spring AI & Gemini LLM",
    INPUT_PLACEHOLDER: "Ask about tech stack, experience, projects...",
    SEND_BTN: "Send",
    INITIAL_MESSAGE: "Hello! I am Satheesh's AI Assistant. Ask me anything about his experience, Java 21 microservices, projects, or tech stack!",
    QUICK_PROMPTS: [
      "What is Satheesh's tech stack?",
      "Tell me about the Portfolio Microservice",
      "What is the Civil Platform project?",
      "How to contact Satheesh?",
    ],
  },

  FOOTER: {
    COPYRIGHT: `© ${new Date().getFullYear()} Satheesh Kumar P. All rights reserved.`,
    STATUS_LIVE: "All Microservices Operational",
    STATUS_BACKEND: "Spring Boot 3 | Kafka | Redis | PostgreSQL",
  },
};
