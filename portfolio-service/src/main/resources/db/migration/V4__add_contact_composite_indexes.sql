-- Flyway Migration V4: Add Composite B-Tree Indexes for High Performance Search & Duplicate Checks
CREATE INDEX IF NOT EXISTS idx_contact_email_created ON contact_messages (email, created_at DESC);
CREATE INDEX IF NOT EXISTS idx_contact_status_created ON contact_messages (status, created_at DESC);
