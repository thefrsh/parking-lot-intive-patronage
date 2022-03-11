package io.github.thefrsh.parkinglot.domain.booking.domain;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.Booker;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;
import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.DomainService;
import lombok.RequiredArgsConstructor;

/**
 * Performs domain operations on {@link Booker}
 * @author Michal Broniewicz
 */
@DomainService
@RequiredArgsConstructor
class BookingServiceImpl implements BookingService {

    private final BookableSpecification bookableSpecification;

    /**
     * Books the parking spot {@code parkingSpot} for the user {@code booker}
     *
     * @param  booker           {@link Booker}
     * @param  parkingSpot      {@link ParkingSpot}
     * @throws BookingException if parking spot has been already booked
     */
    @Override
    public void createBooking(Booker booker, ParkingSpot parkingSpot) {

        bookableSpecification.ifSatisifed(parkingSpot)
                .then(booker::book)
                .otherwise(() -> {
                    throw new BookingException(
                            String.format("Parking spot with id %d has been already taken", parkingSpot.getId())
                    );
                });
    }

    /**
     * Deletes the booking of parking spot {@code parkingSpot} for the user {@code booker}
     *
     * @param  booker           {@link Booker}
     * @param  parkingSpot      {@link ParkingSpot}
     */
    @Override
    public void deleteBooking(Booker booker, ParkingSpot parkingSpot) {

        booker.unbook(parkingSpot);
    }
}
