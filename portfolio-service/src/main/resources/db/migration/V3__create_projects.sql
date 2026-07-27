CREATE TABLE projects (
    id             BIGSERIAL    PRIMARY KEY,
    title          VARCHAR(200) NOT NULL,
    description    TEXT         NOT NULL,
    tech_stack     JSONB,
    github_url     VARCHAR(500),
    live_url       VARCHAR(500),
    case_study_url VARCHAR(500),
    featured       BOOLEAN      NOT NULL DEFAULT FALSE,
    display_order  INTEGER      NOT NULL DEFAULT 0,
    status         VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE',
    created_at     TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_projects_featured ON projects(featured);
CREATE INDEX idx_projects_status   ON projects(status);
CREATE INDEX idx_projects_order    ON projects(display_order);

-- Project 1: Portfolio Microservices Platform (PUBLIC GitHub, ACTIVE)
INSERT INTO projects (title, description, tech_stack, github_url, featured, display_order, status)
VALUES (
    'Portfolio Microservices Platform',
    'Production-grade developer portfolio built with event-driven microservices. Features Spring AI Gemini chatbot, Redis caching, Kafka event streaming, JWT security and AWS cloud deployment.',
    '["Java 21","Spring Boot 3.2","Apache Kafka","Redis","PostgreSQL","React 18","Docker","AWS","Spring Security"]',
    'https://github.com/satheesh1501/portfolio',
    TRUE, 1, 'ACTIVE'
);

-- Project 2: Civil Platform (PRIVATE repo, currently IN_PROGRESS)
INSERT INTO projects (title, description, tech_stack, github_url, featured, display_order, status)
VALUES (
    'Civil Platform',
    'Enterprise SaaS platform for construction project management. Multi-tenant architecture with role-based access control, document management, and real-time project tracking. Private repo — architecture available via Case Study.',
    '["Java","Spring Boot","React","PostgreSQL","Redis","Docker","Microservices","REST API"]',
    NULL,
    TRUE, 2, 'IN_PROGRESS'
);
