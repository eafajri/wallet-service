-- =========================
-- INSERT USERS
-- =========================
INSERT INTO users (id, name, created_at)
VALUES
    (1, 'Alice', NOW()),
    (2, 'Bob', NOW());

-- =========================
-- INSERT WALLETS
-- =========================
INSERT INTO wallets (id, user_id, balance, version, created_at, updated_at)
VALUES
    (1, 1, 100000, 0, NOW(), NOW()),
    (2, 2, 50000, 0, NOW(), NOW());

-- =========================
-- INSERT TRANSACTIONS
-- =========================
INSERT INTO transactions (id, wallet_id, type, amount, status, reference_id, created_at)
VALUES
    (1, 1, 'TOPUP', 100000, 'SUCCESS', 'seed-topup-1', NOW()),
    (2, 2, 'TOPUP', 50000, 'SUCCESS', 'seed-topup-2', NOW());