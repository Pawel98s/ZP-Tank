DROP TABLE IF EXISTS loading_terminal_products;

ALTER TABLE loading_terminals
    DROP COLUMN IF EXISTS quantity;