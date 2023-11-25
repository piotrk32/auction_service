CREATE SEQUENCE if NOT EXISTS public.id_seq AS bigint START WITH 1000;


-- Address table
CREATE TABLE addresses (
                           id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY,
                           country VARCHAR(255),
                           city VARCHAR(255),
                           street VARCHAR(255),
                           zip_code VARCHAR(10),
                           building_number VARCHAR(10),
                           apartment_number VARCHAR(10),
                           is_active BOOLEAN DEFAULT TRUE
);

-- User table
CREATE TABLE users (
                       id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY,
                       address_id BIGINT REFERENCES addresses(id),
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       birth_date DATE,
                       email VARCHAR(255) UNIQUE,
                       phone_number VARCHAR(20),
                       access_token TEXT,
                       refresh_token TEXT,
                       id_token TEXT,
                       status VARCHAR(50) DEFAULT 'REGISTRATION_INCOMPLETE'
);

-- Customer table
CREATE TABLE customers (

    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('id_seq') REFERENCES users(id)
);

-- Provider table
CREATE TABLE providers (
    id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('id_seq') REFERENCES users(id)
);

-- Auction table
CREATE TABLE auctions (
                          id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY,
                          provider_id BIGINT REFERENCES providers(id),
                          auction_name VARCHAR(255),
                          description TEXT,
                          is_active BOOLEAN DEFAULT TRUE,
                          duration INTEGER,
                          price DECIMAL(9, 2),
                          current_bid DECIMAL(9, 2),
                          currency VARCHAR(50)
);

-- Bid table
CREATE TABLE bids (
                      id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY,
                      customer_id BIGINT REFERENCES customers(id),
                      auction_id BIGINT REFERENCES auctions(id),
                      bid_value DECIMAL(9, 2),
                      bid_status VARCHAR(50) DEFAULT 'ACTIVE'
);

-- Item table
CREATE TABLE items (
                       id BIGINT NOT NULL DEFAULT nextval('id_seq') PRIMARY KEY,
                       provider_id BIGINT REFERENCES providers(id),
                       auction_id BIGINT REFERENCES auctions(id),
                       item_name VARCHAR(255),
                       description TEXT,
                       starting_price DECIMAL(10, 2)
);
