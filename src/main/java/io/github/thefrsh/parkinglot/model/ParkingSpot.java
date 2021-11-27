package io.github.thefrsh.parkinglot.model;

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
public class ParkingSpot {

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
    private User owner;
}
