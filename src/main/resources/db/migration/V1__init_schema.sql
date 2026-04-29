-- USERS
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- WALLETS
CREATE TABLE wallets (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL UNIQUE,
                         balance BIGINT NOT NULL DEFAULT 0,
                         version INT NOT NULL DEFAULT 0,
                         created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                         updated_at TIMESTAMP NOT NULL DEFAULT NOW(),

                         CONSTRAINT fk_wallet_user
                             FOREIGN KEY (user_id) REFERENCES users(id)
);

-- TRANSACTIONS
CREATE TABLE transactions (
                              id BIGSERIAL PRIMARY KEY,
                              wallet_id BIGINT NOT NULL,
                              type VARCHAR(20) NOT NULL,
                              amount BIGINT NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              reference_id VARCHAR(100) NOT NULL,
                              created_at TIMESTAMP NOT NULL DEFAULT NOW(),

                              CONSTRAINT fk_tx_wallet
                                  FOREIGN KEY (wallet_id) REFERENCES wallets(id),

                              CONSTRAINT uq_reference_id UNIQUE (reference_id)
);