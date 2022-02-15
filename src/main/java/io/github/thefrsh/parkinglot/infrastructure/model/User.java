package io.github.thefrsh.parkinglot.infrastructure.model;

import io.github.thefrsh.parkinglot.domain.booking.domain.booking.model.ParkingSpotBooker;
import io.github.thefrsh.parkinglot.domain.booking.domain.booking.troubleshooting.exception.BookingException;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

/**
 * User entity representing the user of the system
 * @author Michal Broniewicz
 */
@Entity
@Getter
@NoArgsConstructor
public class User implements ParkingSpotBooker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 2, max = 20)
    @Column(nullable = false, unique = true)
    private String name;

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

    @Override
    public void book(ParkingSpot parkingSpot) {

        getBookedSpots()
                .find(spot -> spot.getId().equals(parkingSpot.getId()))
                .onEmpty(() -> bookedSpots.add(parkingSpot))
                .peek(bookedSpot -> {
                    throw new BookingException(
                            String.format("Parking spot %d has been already booked by user %d", bookedSpot.getId(), id)
                    );
                });
    }

    @Override
    public void unbook(ParkingSpot parkingSpot) {

        getBookedSpots()
                .find(spot -> spot.getId().equals(parkingSpot.getId()))
                .peek(spot -> bookedSpots.remove(parkingSpot))
                .onEmpty(() -> {
                    throw new BookingException(
                            String.format("Parking spot %d is not booked by user %d", parkingSpot.getId(), id));
                });
    }
}
