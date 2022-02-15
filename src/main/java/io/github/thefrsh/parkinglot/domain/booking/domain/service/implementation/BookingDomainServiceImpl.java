package io.github.thefrsh.parkinglot.domain.booking.domain.service.implementation;

import io.github.thefrsh.parkinglot.domain.booking.domain.service.BookingDomainService;
import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpotBooker;
import io.github.thefrsh.parkinglot.infrastructure.model.User;
import io.github.thefrsh.parkinglot.domain.booking.domain.troubleshooting.exception.BookingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Performs domain operations on {@link User}
 * @author Michal Broniewicz
 */
@Service
@RequiredArgsConstructor
public class BookingDomainServiceImpl implements BookingDomainService {

    /**
     * Books the parking spot {@code parkingSpot} for the user {@code booker}
     *
     * @param  booker           {@link User}
     * @param  parkingSpot      {@link ParkingSpot}
     * @throws BookingException if parking spot has been already booked
     */
    @Override
    public void createBooking(ParkingSpotBooker booker, ParkingSpot parkingSpot) {

        if (parkingSpot.bookable()) {

            booker.book(parkingSpot);
        }
        else {

            throw new BookingException(
                    String.format("Parking spot with id %d has been already taken", parkingSpot.getId())
            );
        }
    }

    /**
     * Deletes the booking of parking spot {@code parkingSpot} for the user {@code booker}
     *
     * @param  booker           {@link User}
     * @param  parkingSpot      {@link ParkingSpot}
     */
    @Override
    public void deleteBooking(ParkingSpotBooker booker, ParkingSpot parkingSpot) {

        booker.unbook(parkingSpot);
    }
}
