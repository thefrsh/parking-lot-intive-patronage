package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.dddcqrs.domain.annotation.DomainRepository;
import io.github.thefrsh.parkinglot.domain.booking.model.ParkingSpot;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.ParkingSpotPersistence;
import io.github.thefrsh.parkinglot.infrastructure.persistence.exception.ResourceNotFoundException;
import io.vavr.collection.List;
import lombok.RequiredArgsConstructor;

@DomainRepository
@RequiredArgsConstructor
class ParkingSpotPersistenceRepositoryAdapter implements ParkingSpotPersistence {

    private final ParkingSpotRepository parkingSpotRepository;

    @Override
    public ParkingSpot loadById(Long id) {

        return parkingSpotRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                "Parking spot with id %d is not found".formatted(id)
        ));
    }

    @Override
    public List<ParkingSpot> loadAll() {

        return parkingSpotRepository.findAll();
    }
}
