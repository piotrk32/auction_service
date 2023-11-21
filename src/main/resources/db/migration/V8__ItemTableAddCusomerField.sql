-- Usuń kolumnę customer_id, jeśli już istnieje
ALTER TABLE items DROP COLUMN IF EXISTS customer_id;

-- Dodaj kolumnę customer_id
ALTER TABLE items ADD COLUMN customer_id BIGINT;

-- Dodaj klucz obcy, jeśli tabela customers istnieje
ALTER TABLE items
    ADD CONSTRAINT fk_items_customers
        FOREIGN KEY (customer_id)
            REFERENCES customers (id);