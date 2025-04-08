CREATE TABLE IF NOT EXISTS transactions (
    id SERIAL PRIMARY KEY,
    sender_id INTEGER NOT NULL,
    recipient_id INTEGER NOT NULL,
    amount DECIMAL NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_sender_account FOREIGN KEY (sender_id) REFERENCES accounts(id),
    CONSTRAINT fk_recipient_account FOREIGN KEY (recipient_id) REFERENCES accounts(id)
);