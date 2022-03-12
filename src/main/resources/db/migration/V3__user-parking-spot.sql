CREATE TABLE IF NOT EXISTS users_spots_bookings (
    user_id                      BIGINT,
    parking_spot_id              BIGINT,
    FOREIGN KEY(user_id)         REFERENCES users(id),
    FOREIGN KEY(parking_spot_id) REFERENCES parking_spots(id)
);
