package io.github.thefrsh.parkinglot.query.parkingspot;

import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.Finder;
import io.vavr.collection.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@Finder
@SuppressWarnings(
        value = "unused"
)
@RepositoryRestResource(
        path = "parking-spots",
        collectionResourceRel = "parking-spots"
)
interface ParkingSpotQueryRepository extends Repository<ParkingSpotProjection, Long> {

    @RestResource
    ParkingSpotProjection findById(Long id);
    /**
     * Returns a filtered list of ParkingSpot entities depending on availability
     *
     * @param  available if true - function returns available parking slots only or booked ones otherwise
     * @return List of ParkingSpot objects
     */
    @Query("""
        select p from ParkingSpotProjection p where
        (p.owner is null and ?1 = true) or (p.owner is not null and ?1 = false)
    """)
    @RestResource(path = "by-availability")
    List<ParkingSpotProjection> findAllByOwnerIsNullDependingOnAvailable(Boolean available);

    @RestResource(path = "by-owner")
    List<ParkingSpotProjection> findAllByOwnerId(Long id);
}
