CREATE TABLE IF NOT EXISTS bookings(
    id                      BIGINT  NOT NULL PRIMARY KEY,
    user_id                 BIGINT,
    uuid                    VARCHAR NOT NULL UNIQUE,
    FOREIGN KEY(user_id)    REFERENCES users(id)
);
