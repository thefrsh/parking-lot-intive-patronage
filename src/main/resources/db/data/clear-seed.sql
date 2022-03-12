DELETE FROM users_spots_bookings;
DELETE FROM parking_spots;
DELETE FROM users;

INSERT INTO users (id, name, uuid) VALUES ( 1, 'example', 'b38a8a71-bde7-4c10-829e-9728bbb1e8ec' );

INSERT INTO parking_spots (id, number, storey, disability, uuid)
VALUES ( 1, 1, 1, false, 'bc140bb4-eaee-4bfa-bbf3-51cf6febf964' );

INSERT INTO parking_spots (id, number, storey, disability, uuid)
VALUES ( 2, 2, 1, true, '01f69776-3b7c-4c13-bc9f-e3ebdd642dd3' );

INSERT INTO parking_spots (id, number, storey, disability, uuid)
VALUES ( 3, 3, 1, false, '3382b022-b6d5-4b70-b1be-44cc3e319941' );

INSERT INTO parking_spots (id, number, storey, disability, uuid)
VALUES ( 4, 4, 1, true, '1103bbf0-3e2e-4337-8076-df7b7bfb1e47' );

INSERT INTO parking_spots (id, number, storey, disability, uuid)
VALUES ( 5, 5, 1, false, '155fa9fb-e1e1-41d7-a788-8fb8c7004322' );

INSERT INTO users_spots_bookings (user_id, parking_spot_id) VALUES ( 1, 1 );
