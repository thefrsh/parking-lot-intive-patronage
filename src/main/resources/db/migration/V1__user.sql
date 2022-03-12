CREATE TABLE IF NOT EXISTS users (
    id      BIGINT          NOT NULL PRIMARY KEY,
    name    VARCHAR(20)     NOT NULL UNIQUE,
    uuid    VARCHAR         NOT NULL UNIQUE
);
