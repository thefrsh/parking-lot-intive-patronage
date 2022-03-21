package io.github.thefrsh.parkinglot.webui.booking;

import io.github.thefrsh.parkinglot.domain.booking.model.Booking;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "parking_spots")
@Relation(collectionRelation = "parkingSpots")
class ParkingSpotProjection extends RepresentationModel<ParkingSpotProjection> {

    @Id
    private Long id;

    private Integer number;

    private Integer storey;

    private Boolean disability;

    @ManyToOne
    @JoinTable(
            name = "booking_parkingspots",
            joinColumns = @JoinColumn(name = "parkingspot_id"),
            inverseJoinColumns = @JoinColumn(name = "booking_id")
    )
    @Getter(value = AccessLevel.NONE)
    private Booking booking;
}
