package io.github.thefrsh.parkinglot.application.booking;

public interface BookingApplicationService {

    void createBooking(Long userId, Long parkingSpotId);

    void deleteBooking(Long userId, Long parkingSpotId);
}
