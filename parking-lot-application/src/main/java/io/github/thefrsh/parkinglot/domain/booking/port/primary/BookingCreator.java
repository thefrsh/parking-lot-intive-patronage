package io.github.thefrsh.parkinglot.domain.booking.port.primary;

import io.github.thefrsh.dddcqrs.domain.annotation.DomainEntryPoint;
import io.github.thefrsh.parkinglot.domain.booking.model.Booking;
import io.github.thefrsh.parkinglot.domain.booking.model.ParkingSpot;

@DomainEntryPoint
public interface BookingCreator {

    void createBooking(Booking booking, ParkingSpot parkingSpot);
}
