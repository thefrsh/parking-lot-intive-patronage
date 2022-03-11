package io.github.thefrsh.parkinglot.domain.booking.domain.port.primary;

public interface BookingDeleter {

    void deleteBooking(Long userId, Long spotId);
}
