package io.github.thefrsh.parkinglot.domain.booking.domain.booking.port.incoming;

public interface BookingDeleter {

    void deleteBooking(Long userId, Long spotId);
}
