package io.github.thefrsh.parkinglot.model.repository;

import io.github.thefrsh.parkinglot.model.ParkingSpot;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface ParkingSpotRepository extends Repository<ParkingSpot, Long> {

    Option<ParkingSpot> findById(Long id);

    @Query("select p from ParkingSpot p where (p.owner is null and ?1 = true) or (p.owner is not null and ?1 = false)")
    List<ParkingSpot> findAllByOwnerIsNullDependingOnAvailable(Boolean available);

    List<ParkingSpot> findAll();
}
