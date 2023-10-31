-- Dodanie kolumn do tabeli 'items'
ALTER TABLE items
    ADD COLUMN buy_now_price DOUBLE PRECISION,
    ADD COLUMN is_buy_now_active BOOLEAN DEFAULT FALSE;

-- Dodanie kolumn do tabeli 'auctions'
ALTER TABLE auctions
    ADD COLUMN is_buy_now BOOLEAN DEFAULT FALSE,
    ADD COLUMN buy_now_price DOUBLE PRECISION;