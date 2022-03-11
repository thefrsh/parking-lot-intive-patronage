package io.github.thefrsh.parkinglot.domain.booking.domain;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.Booker;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;

public interface BookingService {

    void createBooking(Booker booker, ParkingSpot parkingSpot);

    void deleteBooking(Booker booker, ParkingSpot parkingSpot);
}
