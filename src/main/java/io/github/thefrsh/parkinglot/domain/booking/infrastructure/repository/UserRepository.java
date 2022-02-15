package io.github.thefrsh.parkinglot.domain.booking.infrastructure.repository;

import io.github.thefrsh.parkinglot.infrastructure.model.User;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.Repository;

import javax.persistence.LockModeType;

/**
 * Repository interface to manage User entity persistence
 * Please see {@link User}
 *
 * @author Michal Broniewicz
 */
public interface UserRepository extends Repository<User, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Option<User> findById(Long id);
}
