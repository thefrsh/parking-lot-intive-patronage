package io.github.thefrsh.parkinglot.domain.booking.infrastructure.repository;

import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Repository interface to manage ParkingSpot entity persistence
 * Please see {@link ParkingSpot}
 *
 * @author Michal Broniewicz
 */
@NoRepositoryBean
public interface ParkingSpotRepository extends Repository<ParkingSpot, Long> {

    Option<ParkingSpot> findById(Long id);

    List<ParkingSpot> findAll();
}
