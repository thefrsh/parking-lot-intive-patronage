package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.dddcqrs.domain.annotation.DomainRepository;
import io.github.thefrsh.parkinglot.domain.booking.model.Booker;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.BookerPersistence;
import io.github.thefrsh.parkinglot.infrastructure.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainRepository
@RequiredArgsConstructor
class BookerPersistenceRepositoryAdapter implements BookerPersistence {

    private final BookerRepository bookerRepository;

    @Override
    public Booker loadById(Long id) throws ResourceNotFoundException {

        return bookerRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                "Booker with id %d is not found".formatted(id)
        ));
    }
}
