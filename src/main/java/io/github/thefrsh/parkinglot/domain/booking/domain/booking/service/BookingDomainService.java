package io.github.thefrsh.parkinglot.domain.booking.domain.booking.service;

import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;
import io.github.thefrsh.parkinglot.domain.booking.domain.booking.model.ParkingSpotBooker;

public interface BookingDomainService {

    void createBooking(ParkingSpotBooker booker, ParkingSpot parkingSpot);

    void deleteBooking(ParkingSpotBooker booker, ParkingSpot parkingSpot);
}
