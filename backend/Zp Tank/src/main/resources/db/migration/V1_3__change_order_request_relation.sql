ALTER TABLE order_requests
    DROP CONSTRAINT fk_order_request_order;

ALTER TABLE order_requests
    DROP COLUMN order_id;

ALTER TABLE orders
    ADD COLUMN order_request_id BIGINT NOT NULL;

ALTER TABLE orders
    ADD CONSTRAINT uk_order_order_request
        UNIQUE (order_request_id);

ALTER TABLE orders
    ADD CONSTRAINT fk_order_order_request
        FOREIGN KEY (order_request_id)
            REFERENCES order_requests(id);

CREATE INDEX idx_orders_order_request
    ON orders(order_request_id);