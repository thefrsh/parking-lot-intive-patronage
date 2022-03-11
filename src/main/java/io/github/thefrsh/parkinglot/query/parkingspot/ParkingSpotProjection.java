package io.github.thefrsh.parkinglot.query.parkingspot;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.Booker;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "parking_spots")
public class ParkingSpotProjection {

    @Id
    private Long id;

    private Integer number;

    private Integer storey;

    private Boolean disability;

    @ManyToOne
    @JoinTable(
            name = "users_spots_bookings",
            joinColumns = @JoinColumn(name = "parking_spot_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @Getter(value = AccessLevel.NONE)
    private Booker owner;
}
