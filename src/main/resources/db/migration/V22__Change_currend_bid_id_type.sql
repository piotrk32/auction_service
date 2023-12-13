ALTER TABLE auctions
    ALTER COLUMN current_bid_id TYPE bigint USING current_bid_id::bigint;