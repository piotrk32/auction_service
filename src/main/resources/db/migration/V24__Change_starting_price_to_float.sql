ALTER TABLE items
    ALTER COLUMN starting_price TYPE float USING starting_price::float;
