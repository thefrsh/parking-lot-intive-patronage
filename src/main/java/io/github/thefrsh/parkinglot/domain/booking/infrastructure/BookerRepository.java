package io.github.thefrsh.parkinglot.domain.booking.infrastructure;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.Booker;
import io.vavr.control.Option;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.Repository;

import javax.persistence.LockModeType;

/**
 * Repository interface to manage User entity persistence
 * Please see {@link Booker}
 *
 * @author Michal Broniewicz
 */
interface BookerRepository extends Repository<Booker, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Option<Booker> findById(Long id);
}
