package io.github.thefrsh.parkinglot.domain.booking.application;

import io.github.thefrsh.parkinglot.domain.booking.domain.port.primary.BookingCreator;
import io.github.thefrsh.parkinglot.domain.booking.domain.port.primary.BookingDeleter;
import io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary.ParkingSpotPersistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary.BookerPersistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.BookingService;
import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@ApplicationService
@RequiredArgsConstructor
class BookingApplicationService implements BookingCreator, BookingDeleter {

    private final BookingService bookingService;
    private final BookerPersistence userPersistence;
    private final ParkingSpotPersistence parkingSpotPersistence;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createBooking(Long userId, Long spotId) {

        var booker = userPersistence.loadById(userId);
        var parkingSpot = parkingSpotPersistence.loadById(spotId);

        bookingService.createBooking(booker, parkingSpot);
    }

    @Override
    @Transactional
    public void deleteBooking(Long userId, Long spotId) {

        var booker = userPersistence.loadById(userId);
        var parkingSpot = parkingSpotPersistence.loadById(spotId);

        bookingService.deleteBooking(booker, parkingSpot);
    }
}
