CREATE TABLE IF NOT EXISTS parking_spots (
    id          BIGINT          NOT NULL PRIMARY KEY,
    number      INT             NOT NULL,
    storey      INT             NOT NULL,
    disability  BOOL            NOT NULL,
    uuid        VARCHAR         NOT NULL UNIQUE
);
