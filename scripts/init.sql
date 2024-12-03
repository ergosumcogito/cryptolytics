CREATE TABLE TrackedCurrency
(
    id     SERIAL PRIMARY KEY,
    symbol VARCHAR(10) NOT NULL UNIQUE,
    name   VARCHAR(50) NOT NULL
);

CREATE TABLE Indicator
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE UserInput
(
    id               SERIAL PRIMARY KEY,
    indicator_name   VARCHAR(50) NOT NULL,
    currencies_names TEXT        NOT NULL
);

CREATE TABLE Query
(
    id                  SERIAL PRIMARY KEY,
    user_id             INTEGER NOT NULL,
    indicator_id        INTEGER NOT NULL,
    query_currencies_id INTEGER,
    FOREIGN KEY (indicator_id) REFERENCES Indicator (id)
);

CREATE TABLE QueryTrackedCurrency
(
    id          SERIAL PRIMARY KEY,
    query_id    INTEGER NOT NULL,
    currency_id INTEGER NOT NULL,
    FOREIGN KEY (query_id) REFERENCES Query (id),
    FOREIGN KEY (currency_id) REFERENCES TrackedCurrency (id)
);

CREATE TABLE IndicatorTrackedCurrency
(
    id           SERIAL PRIMARY KEY,
    indicator_id INTEGER NOT NULL,
    currency_id  INTEGER NOT NULL,
    FOREIGN KEY (indicator_id) REFERENCES Indicator (id),
    FOREIGN KEY (currency_id) REFERENCES TrackedCurrency (id)
);

CREATE TABLE APIData
(
    id                    SERIAL PRIMARY KEY,
    indicator_currency_id INTEGER        NOT NULL,
    timestamp             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    value                 NUMERIC(15, 2) NOT NULL,
    FOREIGN KEY (indicator_currency_id) REFERENCES IndicatorTrackedCurrency (id)
);


INSERT INTO TrackedCurrency (symbol, name)
VALUES ('BTC', 'Bitcoin'),
       ('ETH', 'Ethereum'),
       ('USDT', 'Tether');

INSERT INTO Indicator (name, description)
VALUES ('Price', 'Current price of the currency'),
       ('Volume', '24-hour trading volume');

INSERT INTO UserInput (indicator_name, currencies_names)
VALUES ('Price', 'BTC,ETH'),
       ('Volume', 'BTC,USDT');

INSERT INTO Query (user_id, indicator_id, query_currencies_id)
VALUES (1, 1, 1),
       (2, 2, 1);

INSERT INTO QueryTrackedCurrency (query_id, currency_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 3);

INSERT INTO IndicatorTrackedCurrency (indicator_id, currency_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 3);

INSERT INTO APIData (indicator_currency_id, value)
VALUES (1, 38500.75),
       (2, 1800.25),
       (3, 100000000);
