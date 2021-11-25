package io.github.thefrsh.parkinglot.model;

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
public class User {

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

    public void addParkingSpot(ParkingSpot parkingSpot) {

        bookedSpots.add(parkingSpot);
    }

    public void removeParkingSpot(ParkingSpot parkingSpot) {

        bookedSpots.remove(parkingSpot);
    }

    public io.vavr.collection.List<ParkingSpot> getBookedSpots() {

        return io.vavr.collection.List.ofAll(bookedSpots);
    }
}
