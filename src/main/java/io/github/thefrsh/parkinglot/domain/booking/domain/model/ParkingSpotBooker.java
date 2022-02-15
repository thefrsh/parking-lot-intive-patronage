package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;

public interface ParkingSpotBooker {

    void book(ParkingSpot parkingSpot);

    void unbook(ParkingSpot parkingSpot);
}
