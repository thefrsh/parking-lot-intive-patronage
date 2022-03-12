package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.booking.domain.BookingException;
import io.github.thefrsh.parkinglot.domain.sharedkernel.BaseAggregateRoot;
import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.DomainAggregateRoot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * User entity representing parking spot booker
 * @author Michal Broniewicz
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@DomainAggregateRoot
public class Booker extends BaseAggregateRoot {

    @OneToMany
    @JoinTable(
            name = "users_spots_bookings",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "parking_spot_id")
    )
    @Getter(value = AccessLevel.NONE)
    private List<ParkingSpot> bookedSpots;

    public io.vavr.collection.List<ParkingSpot> getBookedSpots() {

        return io.vavr.collection.List.ofAll(bookedSpots);
    }

    public void book(ParkingSpot parkingSpot) {

        getBookedSpots()
                .find(parkingSpot::equals)
                .onEmpty(() -> bookedSpots.add(parkingSpot))
                .peek(bookedSpot -> {
                    throw new BookingException(
                            "Parking spot has been already booked by user %d".formatted(id)
                    );
                });
    }

    public void unbook(ParkingSpot parkingSpot) {

        getBookedSpots()
                .find(parkingSpot::equals)
                .peek(bookedSpots::remove)
                .onEmpty(() -> {
                    throw new BookingException(
                            "Parking spot is not booked by user %d".formatted(id)
                    );
                });
    }
}
