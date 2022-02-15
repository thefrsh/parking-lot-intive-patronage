package io.github.thefrsh.parkinglot.domain.booking.application.service;

import io.github.thefrsh.parkinglot.domain.booking.domain.booking.port.incoming.BookingCreator;
import io.github.thefrsh.parkinglot.domain.booking.domain.booking.port.incoming.BookingDeleter;
import io.github.thefrsh.parkinglot.domain.booking.domain.booking.port.outgoing.ParkingSpotPersistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.booking.port.outgoing.UserPersistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.booking.service.BookingDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingApplicationService implements BookingCreator, BookingDeleter {

    private final BookingDomainService bookingDomainService;
    private final UserPersistence userPersistence;
    private final ParkingSpotPersistence parkingSpotPersistence;

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createBooking(Long userId, Long spotId) {

        var booker = userPersistence.loadById(userId);
        var parkingSpot = parkingSpotPersistence.loadById(spotId);

        bookingDomainService.createBooking(booker, parkingSpot);
    }

    @Override
    @Transactional
    public void deleteBooking(Long userId, Long spotId) {

        var booker = userPersistence.loadById(userId);
        var parkingSpot = parkingSpotPersistence.loadById(spotId);

        bookingDomainService.deleteBooking(booker, parkingSpot);
    }
}
