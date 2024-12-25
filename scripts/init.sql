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
       ('Volume', '24-hour trading volume');

-- Insert test data into CurrencyIndicators (mapping currency and indicator)
INSERT INTO Currency_Indicator (currency_id, indicator_id)
VALUES (1, 1), -- BTC - Price
       (2, 1), -- ETH - Price
       (1, 2), -- BTC - Volume
       (2, 2), -- ETH - Volume
       (3, 1);
-- USDT - Price

-- Insert test data into CurrencyIndicatorValues
INSERT INTO Currency_Indicator_Value (currency_indicator_id, value)
VALUES (1, 38500.75),  -- BTC - Price
       (2, 2800.45),   -- ETH - Price
       (3, 100000000), -- BTC - Volume
       (4, 1500000),   -- ETH - Volume
       (5, 1); -- USDT - Price
