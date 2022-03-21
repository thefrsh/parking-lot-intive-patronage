package io.github.thefrsh.parkinglot.domain.booking.model;

import io.github.thefrsh.dddcqrs.domain.BaseEntity;
import io.github.thefrsh.dddcqrs.domain.annotation.DomainEntity;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * ParkingSpot entity representing single parking spot in the parking lot
 * @author Michal Broniewicz
 */
@Entity
@NoArgsConstructor
@Table(name = "parking_spots")
@DomainEntity
@SuppressWarnings(value = "unused")
public class ParkingSpot extends BaseEntity {

    @Embedded
    private Number number;

    @Embedded
    private Storey storey;

    @Column(nullable = false)
    private Boolean disability;

    @ManyToOne
    @JoinTable(
            name = "booking_parkingspots",
            joinColumns = @JoinColumn(name = "parkingspot_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    private Booking booking;

    public boolean bookable() {

        return booking == null;
    }
}
