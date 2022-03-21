package io.github.thefrsh.parkinglot.domain.booking.model;

import io.github.thefrsh.dddcqrs.domain.BaseAggregateRoot;
import io.github.thefrsh.dddcqrs.domain.annotation.DomainAggregateRoot;
import io.github.thefrsh.parkinglot.domain.booking.exception.BookingException;
import io.vavr.control.Try;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@DomainAggregateRoot
@Table(name = "bookings")
@SuppressWarnings(value = "unused")
public class Booking extends BaseAggregateRoot {

    public Booking(Booker booker) {

        this.booker = booker;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Booker booker;

    @OneToMany
    @JoinTable(
            name = "booking_parkingspots",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "parkingspot_id")
    )
    private List<ParkingSpot> bookedParkingSpots;

    public io.vavr.collection.List<ParkingSpot> getBookedSpots() {

        return io.vavr.collection.List.ofAll(bookedParkingSpots);
    }

    public void bookParkingSpot(ParkingSpot parkingSpot) {

        bookedParkingSpots.add(parkingSpot);
    }

    public void unbookParkingSpot(ParkingSpot parkingSpot) {

        if(!bookedParkingSpots.removeIf(parkingSpot::equals)) {
            informThat("Parking spot with id %d is not booked by this user".formatted(parkingSpot.getId()));
        }
    }

    private void informThat(String message) {

        throw new BookingException(message);
    }
}
