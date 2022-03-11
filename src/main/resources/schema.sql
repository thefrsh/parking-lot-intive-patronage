CREATE TABLE IF NOT EXISTS users (
    id      BIGINT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(20)     NOT NULL UNIQUE,
    uuid    VARCHAR         NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS parking_spots (
    id          BIGINT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    number      INT             NOT NULL,
    storey      INT             NOT NULL,
    disability  BOOL            NOT NULL,
    uuid        VARCHAR         NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users_spots_bookings (
    user_id                      BIGINT,
    parking_spot_id              BIGINT,
    FOREIGN KEY(user_id)         REFERENCES users(id),
    FOREIGN KEY(parking_spot_id) REFERENCES parking_spots(id)
)
