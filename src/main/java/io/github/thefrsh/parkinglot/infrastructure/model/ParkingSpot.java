package io.github.thefrsh.parkinglot.infrastructure.model;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.Bookable;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * ParkingSpot entity representing single parking spot in the parking lot
 * @author Michal Broniewicz
 */
@Entity
@Getter
@NoArgsConstructor
public class ParkingSpot implements Bookable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Column(nullable = false)
    private Integer number;

    @Min(1)
    @Column(nullable = false)
    private Integer storey;

    @Column(nullable = false)
    private Boolean disability;

    @ManyToOne
    @JoinTable(
            name = "users_spots_bookings",
            joinColumns = @JoinColumn(name = "parking_spot_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Getter(value = AccessLevel.NONE)
    private User owner;

    public Option<User> getOwner() {

        return Option.of(owner);
    }

    @Override
    public boolean bookable() {

        return owner == null;
    }
}
