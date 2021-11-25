package io.github.thefrsh.parkinglot.model.repository;

import io.github.thefrsh.parkinglot.model.User;
import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

/**
 * Repository interface to manage User entity persistence
 * Please see {@link io.github.thefrsh.parkinglot.model.User}
 *
 * @author Michal Broniewicz
 */
public interface UserRepository extends Repository<User, Long> {

    Option<User> findById(Long id);
}
