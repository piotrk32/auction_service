ALTER TABLE bids
    ALTER COLUMN bid_value TYPE float USING bid_value::float;