ALTER TABLE auctions
    DROP COLUMN is_active;

-- Dodanie kolumny status_auction
ALTER TABLE auctions
    ADD COLUMN status_auction VARCHAR(255);