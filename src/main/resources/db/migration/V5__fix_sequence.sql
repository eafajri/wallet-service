-- =========================
-- FIX SEQUENCE (POSTGRES)
-- =========================

SELECT setval(
               pg_get_serial_sequence('users', 'id'),
               COALESCE((SELECT MAX(id) FROM users), 1),
               true
       );

SELECT setval(
               pg_get_serial_sequence('wallets', 'id'),
               COALESCE((SELECT MAX(id) FROM wallets), 1),
               true
       );

SELECT setval(
               pg_get_serial_sequence('transactions', 'id'),
               COALESCE((SELECT MAX(id) FROM transactions), 1),
               true
       );