-- V4__Add_fields_to_auctions.sql

ALTER TABLE auctions
    ADD COLUMN is_buy_now_completed BOOLEAN NOT NULL DEFAULT FALSE;

ALTER TABLE auctions
    ADD COLUMN buy_now_customer_id BIGINT;

-- Tworzenie klucza obcego, tylko jeśli tabela customers już istnieje
ALTER TABLE auctions
    ADD CONSTRAINT fk_buy_now_customer
        FOREIGN KEY (buy_now_customer_id)
            REFERENCES customers (id)
            ON DELETE SET NULL;
