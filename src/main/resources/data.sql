INSERT INTO users (id, name) VALUES ( 1, 'example' );

INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 1, 1, 1, false );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 2, 2, 1, true );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 3, 3, 1, false );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 4, 4, 1, true );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 5, 5, 1, false );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 6, 1, 2, true );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 7, 2, 2, false );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 8, 3, 2, true );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 9, 4, 2, false );
INSERT INTO parking_spots (id, number, storey, disability) VALUES ( 10, 5, 2, true );

INSERT INTO users_spots_bookings (user_id, parking_spot_id) VALUES ( 1, 1 );
