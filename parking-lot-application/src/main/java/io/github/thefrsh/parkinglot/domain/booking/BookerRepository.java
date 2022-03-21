package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.parkinglot.domain.booking.model.Booker;
import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

interface BookerRepository extends Repository<Booker, Long> {

     Option<Booker> findById(Long id);
}
