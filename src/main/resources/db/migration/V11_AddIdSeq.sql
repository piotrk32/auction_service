-- Tworzenie sekwencji
CREATE SEQUENCE if NOT EXISTS public.id_seq AS bigint START WITH 1000;

-- Modyfikacja tabeli addresses
ALTER TABLE addresses ALTER COLUMN id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY;

-- Modyfikacja tabeli auction_items
ALTER TABLE auction_items ALTER COLUMN id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY;

-- Modyfikacja tabeli auctions
ALTER TABLE auctions ALTER COLUMN id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY;

-- Modyfikacja tabeli bids
ALTER TABLE bids ALTER COLUMN id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY;

-- Modyfikacja tabeli customers
ALTER TABLE customers ALTER COLUMN id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY;

-- Modyfikacja tabeli items
ALTER TABLE items ALTER COLUMN id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY;

-- Modyfikacja tabeli providers
ALTER TABLE providers ALTER COLUMN id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY

-- Modyfikacja tabeli users
ALTER TABLE users ALTER COLUMN id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY