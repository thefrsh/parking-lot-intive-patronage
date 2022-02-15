package io.github.thefrsh.parkinglot.query.repository;

import io.github.thefrsh.parkinglot.domain.booking.infrastructure.repository.ParkingSpotRepository;
import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;
import io.github.thefrsh.parkinglot.query.projection.ParkingSpotProjection;
import io.vavr.collection.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@SuppressWarnings(
        value = "unused"
)
@RepositoryRestResource(
        path = "parking-spots",
        collectionResourceRel = "parkingSpot",
        excerptProjection = ParkingSpotProjection.class
)
public interface ParkingSpotQueryRepository extends ParkingSpotRepository {

    /**
     * Returns a filtered list of ParkingSpot entities depending on availability
     *
     * @param  available if true - function returns available parking slots only or booked ones otherwise
     * @return List of ParkingSpot objects
     */
    @RestResource(path = "by-availability")
    @Query("select p from ParkingSpot p where (p.owner is null and ?1 = true) or (p.owner is not null and ?1 = false)")
    List<ParkingSpot> findAllByOwnerIsNullDependingOnAvailable(Boolean available);

    @RestResource(path = "by-owner")
    List<ParkingSpot> findAllByOwnerId(Long id);
}
