CREATE TABLE contact_messages (
    id          BIGSERIAL    PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL,
    subject     VARCHAR(200) NOT NULL,
    message     TEXT         NOT NULL,
    ip_address  VARCHAR(45),
    status      VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_contact_email   ON contact_messages(email);
CREATE INDEX idx_contact_status  ON contact_messages(status);
CREATE INDEX idx_contact_created ON contact_messages(created_at);
