package io.github.thefrsh.parkinglot.domain.booking.domain.model;

import io.github.thefrsh.parkinglot.domain.sharedkernel.BaseEntity;
import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.DomainEntity;
import io.vavr.control.Option;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ParkingSpot entity representing single parking spot in the parking lot
 * @author Michal Broniewicz
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "parking_spots")
@DomainEntity
public class ParkingSpot extends BaseEntity {

    @Embedded
    private Number number;

    @Embedded
    private Storey storey;

    @Column(nullable = false)
    private Boolean disability;

    @ManyToOne
    @JoinTable(
            name = "users_spots_bookings",
            joinColumns = @JoinColumn(name = "parking_spot_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Getter(value = AccessLevel.NONE)
    private Booker owner;

    public Option<Booker> getOwner() {

        return Option.of(owner);
    }

    public boolean bookable() {

        return owner == null;
    }
}
