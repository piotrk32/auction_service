ALTER TABLE items
ADD COLUMN customer_id BIGINT NULL;

ALTER TABLE items
ADD CONSTRAINT fk_items_customers
FOREIGN KEY (customer_id)
REFERENCES customers (id);