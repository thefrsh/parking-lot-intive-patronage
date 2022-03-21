package io.github.thefrsh.parkinglot.application.booking;

import io.github.thefrsh.dddcqrs.application.annotation.ApplicationService;
import io.github.thefrsh.parkinglot.domain.booking.port.primary.BookingCreator;
import io.github.thefrsh.parkinglot.domain.booking.port.primary.BookingDeleter;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.BookerPersistence;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.ParkingSpotPersistence;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.BookingPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
class BookingApplicationServiceImpl implements BookingApplicationService {

    private final BookingCreator bookingCreator;
    private final BookingDeleter bookingDeleter;

    private final BookerPersistence bookerPersistence;
    private final BookingPersistence bookingPersistence;
    private final ParkingSpotPersistence parkingSpotPersistence;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createBooking(Long userId, Long parkingSpotId) {

        var booker = bookerPersistence.loadById(userId);
        var booking = bookingPersistence.loadByBooker(booker);
        var parkingSpot = parkingSpotPersistence.loadById(parkingSpotId);

        bookingCreator.createBooking(booking, parkingSpot);
    }

    @Override
    public void deleteBooking(Long userId, Long parkingSpotId) {

        var booker = bookerPersistence.loadById(userId);
        var booking = bookingPersistence.loadByBooker(booker);
        var parkingSpot = parkingSpotPersistence.loadById(parkingSpotId);

        bookingDeleter.deleteBooking(booking, parkingSpot);
    }
}
