package io.github.thefrsh.parkinglot.domain.booking.domain.port.incoming;

public interface BookingCreator {

    void createBooking(Long userId, Long spotId);
}
