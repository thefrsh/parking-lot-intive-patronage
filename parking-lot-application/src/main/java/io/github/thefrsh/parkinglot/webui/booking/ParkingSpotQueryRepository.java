package io.github.thefrsh.parkinglot.webui.booking;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

interface ParkingSpotQueryRepository extends Repository<ParkingSpotProjection, Long> {

    Option<ParkingSpotProjection> findById(Long id);

    /**
     * Returns a filtered list of ParkingSpot entities depending on availability
     *
     * @param  bookable if true - function returns available parking slots only or booked ones otherwise
     * @return List of ParkingSpot objects
     */
    @Query("""
        select p from ParkingSpotProjection p where
        (p.booking is null and ?1 = true) or (p.booking is not null and ?1 = false)
    """)
    List<ParkingSpotProjection> findAllByBookability(Boolean bookable);

    @Query("select p from ParkingSpotProjection p where p.booking.booker.id = ?1")
    List<ParkingSpotProjection> findAllByOwnerId(Long id);
}
