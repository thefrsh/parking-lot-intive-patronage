CREATE TABLE IF NOT EXISTS booking_parkingspots(
    booking_id                  BIGINT,
    parkingspot_id              BIGINT,
    FOREIGN KEY(booking_id)     REFERENCES bookings(id),
    FOREIGN KEY(parkingspot_id) REFERENCES parking_spots(id)
);
