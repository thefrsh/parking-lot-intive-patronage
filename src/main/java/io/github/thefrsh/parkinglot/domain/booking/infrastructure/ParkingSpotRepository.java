package io.github.thefrsh.parkinglot.domain.booking.infrastructure;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

/**
 * Repository interface to manage ParkingSpot entity persistence
 * Please see {@link ParkingSpot}
 *
 * @author Michal Broniewicz
 */
interface ParkingSpotRepository extends Repository<ParkingSpot, Long> {

    Option<ParkingSpot> findById(Long id);

    List<ParkingSpot> findAll();
}
