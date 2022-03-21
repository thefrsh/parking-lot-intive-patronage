package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.dddcqrs.domain.annotation.DomainService;
import io.github.thefrsh.parkinglot.domain.booking.exception.BookingException;
import io.github.thefrsh.parkinglot.domain.booking.model.Booking;
import io.github.thefrsh.parkinglot.domain.booking.model.ParkingSpot;
import io.github.thefrsh.parkinglot.domain.booking.port.primary.BookingCreator;
import io.github.thefrsh.parkinglot.domain.booking.port.primary.BookingDeleter;
import lombok.RequiredArgsConstructor;

/**
 * Performs domain operations on {@link Booking}
 * @author Michal Broniewicz
 */
@DomainService
@RequiredArgsConstructor
class BookingService implements BookingCreator, BookingDeleter {

    private final BookableSpecification bookableSpecification;

    /**
     * Books the parking spot {@code parkingSpot} for the user {@code booker}
     *
     * @param  booking          {@link Booking}
     * @param  parkingSpot      {@link ParkingSpot}
     * @throws BookingException if parking spot has been already booked
     */
    @Override
    public void createBooking(Booking booking, ParkingSpot parkingSpot) {

        bookableSpecification.ifSatisfiedBy(parkingSpot)
                .then(booking::bookParkingSpot)
                .otherwise(() -> informThat(
                        "Parking spot with id %d has been already booked".formatted(parkingSpot.getId())
                ));
    }

    /**
     * Deletes the booking of parking spot {@code parkingSpot} for the user {@code booker}
     *
     * @param  booking          {@link Booking}
     * @param  parkingSpot      {@link ParkingSpot}
     */
    @Override
    public void deleteBooking(Booking booking, ParkingSpot parkingSpot) {

        booking.unbookParkingSpot(parkingSpot);
    }

    private void informThat(String message) {

        throw new BookingException(message);
    }
}
