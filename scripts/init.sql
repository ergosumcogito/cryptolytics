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
