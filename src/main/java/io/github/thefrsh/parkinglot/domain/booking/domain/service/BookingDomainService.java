package io.github.thefrsh.parkinglot.domain.booking.domain.service;

import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpotBooker;

public interface BookingDomainService {

    void createBooking(ParkingSpotBooker booker, ParkingSpot parkingSpot);

    void deleteBooking(ParkingSpotBooker booker, ParkingSpot parkingSpot);
}
