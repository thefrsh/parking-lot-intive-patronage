package io.github.thefrsh.parkinglot.domain.booking.infrastructure;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.Booker;
import io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary.BookerPersistence;
import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.DomainRepository;
import io.github.thefrsh.parkinglot.infrastructure.persistence.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainRepository
@RequiredArgsConstructor
class BookerPersistenceJpaRepositoryAdapter implements BookerPersistence {

    private final BookerRepository bookerRepository;

    @Override
    public Booker loadById(Long id) {

        return bookerRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id %d is not found", id)
        ));
    }
}
