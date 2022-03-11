package io.github.thefrsh.parkinglot.domain.booking.infrastructure;

import io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary.ParkingSpotPersistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;
import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.DomainRepository;
import io.github.thefrsh.parkinglot.infrastructure.persistence.ResourceNotFoundException;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;

@DomainRepository
@RequiredArgsConstructor
class ParkingSpotPersistenceJpaRepositoryAdapter implements ParkingSpotPersistence {

    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    public ParkingSpot loadById(Long id) {

        return parkingSpotRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                String.format("Parking spot with id %d is not found", id)
        ));
    }

    @Override
    public List<ParkingSpot> loadAll() {

        return parkingSpotRepository.findAll();
    }
}
