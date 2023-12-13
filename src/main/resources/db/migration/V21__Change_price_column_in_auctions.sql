ALTER TABLE auctions
    ALTER COLUMN price TYPE float USING price::float;