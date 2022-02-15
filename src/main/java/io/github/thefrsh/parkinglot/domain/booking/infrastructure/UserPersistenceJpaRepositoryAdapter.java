package io.github.thefrsh.parkinglot.domain.booking.infrastructure;

import io.github.thefrsh.parkinglot.domain.booking.domain.port.outgoing.UserPersistence;
import io.github.thefrsh.parkinglot.domain.booking.infrastructure.repository.UserRepository;
import io.github.thefrsh.parkinglot.infrastructure.model.User;
import io.github.thefrsh.parkinglot.domain.booking.infrastructure.troubleshooting.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPersistenceJpaRepositoryAdapter implements UserPersistence {

    private final UserRepository userRepository;

    @Override
    public User loadById(Long id) {

        return userRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id %d is not found", id)
        ));
    }
}
