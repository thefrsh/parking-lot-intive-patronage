package io.github.thefrsh.parkinglot.domain.booking.domain.port.incoming;

public interface BookingDeleter {

    void deleteBooking(Long userId, Long spotId);
}
