-- ensure amount always positive
ALTER TABLE transactions
    ADD CONSTRAINT chk_amount_positive CHECK (amount > 0);

-- ensure balance never negative
ALTER TABLE wallets
    ADD CONSTRAINT chk_balance_non_negative CHECK (balance >= 0);