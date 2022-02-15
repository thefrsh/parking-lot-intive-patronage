package io.github.thefrsh.parkinglot.domain.booking.infrastructure;

import io.github.thefrsh.parkinglot.domain.booking.domain.port.outgoing.ParkingSpotPersistence;
import io.github.thefrsh.parkinglot.domain.booking.infrastructure.repository.ParkingSpotRepository;
import io.github.thefrsh.parkinglot.domain.booking.infrastructure.troubleshooting.exception.ResourceNotFoundException;
import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParkingSpotPersistenceJpaRepositoryAdapter implements ParkingSpotPersistence {

    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    public ParkingSpot loadById(Long id) {

        return parkingSpotRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                String.format("Parking spot with id %d is not found", id)
        ));
    }
}
