CREATE TABLE auction_items (
    auction_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    PRIMARY KEY (auction_id, item_id),
    FOREIGN KEY (auction_id) REFERENCES auctions(id),
    FOREIGN KEY (item_id) REFERENCES items(id)
);
