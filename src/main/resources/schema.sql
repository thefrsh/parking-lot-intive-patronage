CREATE TABLE IF NOT EXISTS user (
    id      BIGINT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(20)     NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS parking_spot (
    id          BIGINT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    number      INT             NOT NULL,
    storey      INT             NOT NULL,
    disability  BOOL            NOT NULL
);

CREATE TABLE IF NOT EXISTS users_spots_bookings (
    user_id                      BIGINT,
    parking_spot_id              BIGINT,
    FOREIGN KEY(user_id)         REFERENCES user(id),
    FOREIGN KEY(parking_spot_id) REFERENCES parking_spot(id)
)
