-- Dodanie kolumn created_at i modified_at do tabeli addresses
ALTER TABLE addresses
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ADD COLUMN modified_at TIMESTAMP WITHOUT TIME ZONE;

-- Dodanie kolumn created_at i modified_at do tabeli auction_items
ALTER TABLE auction_items
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ADD COLUMN modified_at TIMESTAMP WITHOUT TIME ZONE;

-- Dodanie kolumn created_at i modified_at do tabeli auctions
ALTER TABLE auctions
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ADD COLUMN modified_at TIMESTAMP WITHOUT TIME ZONE;

-- Dodanie kolumn created_at i modified_at do tabeli bids
ALTER TABLE bids
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ADD COLUMN modified_at TIMESTAMP WITHOUT TIME ZONE;

-- Dodanie kolumn created_at i modified_at do tabeli customers
ALTER TABLE customers
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ADD COLUMN modified_at TIMESTAMP WITHOUT TIME ZONE;

-- Dodanie kolumn created_at i modified_at do tabeli items
ALTER TABLE items
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ADD COLUMN modified_at TIMESTAMP WITHOUT TIME ZONE;

-- Dodanie kolumn created_at i modified_at do tabeli providers
ALTER TABLE providers
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ADD COLUMN modified_at TIMESTAMP WITHOUT TIME ZONE;

-- Dodanie kolumn created_at i modified_at do tabeli users
ALTER TABLE users
    ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    ADD COLUMN modified_at TIMESTAMP WITHOUT TIME ZONE;