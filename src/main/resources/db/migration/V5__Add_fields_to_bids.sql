-- 5__Add_fields_to_bids.sql

ALTER TABLE bids
    ADD COLUMN is_winning BOOLEAN NOT NULL DEFAULT FALSE;
