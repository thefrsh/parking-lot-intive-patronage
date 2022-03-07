package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.booking.domain.BookingException;
import io.github.thefrsh.parkinglot.domain.sharedkernel.BaseAggregateRoot;
import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.DomainAggregateRoot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

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
                .find(spot -> spot.equals(parkingSpot))
                .onEmpty(() -> bookedSpots.add(parkingSpot))
                .peek(bookedSpot -> {
                    throw new BookingException(
                            String.format("Parking spot has been already booked by user %d", id)
                    );
                });
    }

    public void unbook(ParkingSpot parkingSpot) {

        getBookedSpots()
                .find(spot -> spot.equals(parkingSpot))
                .peek(spot -> bookedSpots.remove(parkingSpot))
                .onEmpty(() -> {
                    throw new BookingException(
                            String.format("Parking spot is not booked by user %d", id));
                });
    }
}

