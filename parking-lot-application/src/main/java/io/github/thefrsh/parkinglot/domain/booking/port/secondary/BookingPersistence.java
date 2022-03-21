package io.github.thefrsh.parkinglot.domain.booking.port.secondary;

import io.github.thefrsh.dddcqrs.infrastructure.Persistence;
import io.github.thefrsh.parkinglot.domain.booking.model.Booker;
import io.github.thefrsh.parkinglot.domain.booking.model.Booking;

public interface BookingPersistence extends Persistence<Booking> {

    Booking loadByBooker(Booker booker);
}
