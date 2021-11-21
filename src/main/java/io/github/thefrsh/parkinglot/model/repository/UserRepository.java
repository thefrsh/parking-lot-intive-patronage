package io.github.thefrsh.parkinglot.model.repository;

import io.github.thefrsh.parkinglot.model.User;
import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    Option<User> findById(Long id);
}
