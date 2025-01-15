-- Create table for currencies
CREATE TABLE Currency
(
    id     SERIAL PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL UNIQUE,
    name   VARCHAR(50) NOT NULL
);

-- Create table for indicators
CREATE TABLE Indicator
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

-- Create table for CurrencyIndicator (linking currency and indicator)
CREATE TABLE Currency_Indicator
(
    id           SERIAL PRIMARY KEY,
    currency_id  INTEGER NOT NULL,
    indicator_id INTEGER NOT NULL,
    FOREIGN KEY (currency_id) REFERENCES Currency (id),
    FOREIGN KEY (indicator_id) REFERENCES Indicator (id)
);

-- Create table for storing the values of indicators for specific currency at a specific time
CREATE TABLE Currency_Indicator_Value
(
    id                    SERIAL PRIMARY KEY,
    currency_indicator_id INTEGER        NOT NULL,
    value                 NUMERIC(15, 2) NOT NULL,
    timestamp             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (currency_indicator_id) REFERENCES Currency_Indicator (id)
);

-- Insert test data into currencies
INSERT INTO Currency (symbol, name)
VALUES ('BTC', 'Bitcoin'),
       ('ETH', 'Ethereum'),
       ('USDT', 'Tether');

-- Insert test data into indicators
INSERT INTO Indicator (name, description)
VALUES ('Price', 'Current price of the currency'),
       ('Volume', '24-hour trading volume'),
       ('Market_Cap', 'Market capitalization of the currency'),
       ('Market_Cap_Rank', 'Rank by market capitalization'),
       ('High_24h', 'Highest price in the last 24 hours');

-- Insert test data into CurrencyIndicators (mapping currency and indicator)
INSERT INTO Currency_Indicator (currency_id, indicator_id)
VALUES (1, 1), -- BTC - Price
       (2, 1), -- ETH - Price
       (1, 2), -- BTC - Volume
       (2, 2), -- ETH - Volume
       (3, 1), -- USDT - Price
       (1, 3), -- BTC - Market Cap
       (2, 3), -- ETH - Market Cap
       (3, 3), -- USDT - Market Cap
       (1, 4), -- BTC - Market Cap Rank
       (2, 4), -- ETH - Market Cap Rank
       (3, 4), -- USDT - Market Cap Rank
       (1, 5), -- BTC - High 24h
       (2, 5), -- ETH - High 24h
       (3, 5); -- USDT - High 24h

-- Insert test data into CurrencyIndicatorValues
INSERT INTO Currency_Indicator_Value (currency_indicator_id, value)
VALUES (1, 38500.75),  -- BTC - Price
       (2, 2800.45),   -- ETH - Price
       (3, 100000000), -- BTC - Volume
       (4, 1500000),   -- ETH - Volume
       (5, 1),         -- USDT - Price
       (6, 720000000000), -- BTC - Market Cap
       (7, 320000000000), -- ETH - Market Cap
       (8, 83000000),     -- USDT - Market Cap
       (9, 1),            -- BTC - Market Cap Rank
       (10, 2),           -- ETH - Market Cap Rank
       (11, 3),           -- USDT - Market Cap Rank
       (12, 39000),       -- BTC - High 24h
       (13, 3000),        -- ETH - High 24h
       (14, 1.01);        -- USDT - High 24h


-- Insert test data into CurrencyIndicatorValues with various timestamps
INSERT INTO Currency_Indicator_Value (currency_indicator_id, value, timestamp)
VALUES
    (1, 38500.75, '2025-01-01 10:00:00'),  -- BTC - Price, Jan 1, 2025
    (1, 38900.40, '2025-01-02 10:00:00'),  -- BTC - Price, Jan 2, 2025
    (1, 39500.20, '2025-01-03 10:00:00'),  -- BTC - Price, Jan 3, 2025
    (1, 39250.55, '2025-01-04 10:00:00'),  -- BTC - Price, Jan 4, 2025
    (1, 38000.10, '2025-01-05 10:00:00'),  -- BTC - Price, Jan 5, 2025
    (1, 37500.80, '2025-01-06 10:00:00'),  -- BTC - Price, Jan 6, 2025
    (1, 37000.60, '2025-01-07 10:00:00');  -- BTC - Price, Jan 7, 2025

-- Insert test data for other indicators (Volume, Market Cap, etc.) with timestamps
INSERT INTO Currency_Indicator_Value (currency_indicator_id, value, timestamp)
VALUES
    (3, 100000000, '2025-01-01 10:00:00'),  -- BTC - Volume, Jan 1, 2025
    (6, 720000000000, '2025-01-01 10:00:00'), -- BTC - Market Cap, Jan 1, 2025
    (9, 1, '2025-01-01 10:00:00'),  -- BTC - Market Cap Rank, Jan 1, 2025
    (12, 39000, '2025-01-01 10:00:00'), -- BTC - High 24h, Jan 1, 2025
    (2, 2800.45, '2025-01-01 10:00:00'),   -- ETH - Price, Jan 1, 2025
    (5, 1, '2025-01-01 10:00:00'),         -- USDT - Price, Jan 1, 2025
    (4, 1500000, '2025-01-01 10:00:00');   -- ETH - Volume, Jan 1, 2025

-- Create table for API Keys
CREATE TABLE ApiKeys (
                         id SERIAL PRIMARY KEY,
                         api_key VARCHAR(128) NOT NULL UNIQUE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert a test API key
INSERT INTO ApiKeys (api_key) VALUES ('test-api-key-123');

-- Create table for Saved Queries
CREATE TABLE SavedQueries (
                              id SERIAL PRIMARY KEY,
                              api_key_id INTEGER NOT NULL,
                              currency_id INTEGER NOT NULL,
                              indicator_id INTEGER NOT NULL,
                              FOREIGN KEY (api_key_id) REFERENCES ApiKeys (id),
                              FOREIGN KEY (currency_id) REFERENCES Currency (id),
                              FOREIGN KEY (indicator_id) REFERENCES Indicator (id)
);

-- Insert a test saved query
INSERT INTO SavedQueries (api_key_id, currency_id, indicator_id) VALUES (1, 1, 1);
