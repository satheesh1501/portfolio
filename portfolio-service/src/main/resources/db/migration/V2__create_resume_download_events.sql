CREATE TABLE resume_download_events (
    id             BIGSERIAL    PRIMARY KEY,
    ip_address     VARCHAR(45),
    user_agent     TEXT,
    referer        VARCHAR(500),
    downloaded_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_resume_ip   ON resume_download_events(ip_address);
CREATE INDEX idx_resume_date ON resume_download_events(downloaded_at);
