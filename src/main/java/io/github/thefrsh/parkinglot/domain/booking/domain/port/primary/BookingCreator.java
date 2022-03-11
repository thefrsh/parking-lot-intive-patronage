package io.github.thefrsh.parkinglot.domain.booking.domain.port.primary;

public interface BookingCreator {

    void createBooking(Long userId, Long spotId);
}
