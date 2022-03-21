package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.parkinglot.domain.booking.model.Booker;
import io.github.thefrsh.parkinglot.domain.booking.model.Booking;
import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

/**
 * Repository interface to manage Booking entity persistence
 * Please see {@link Booking}
 *
 * @author Michal Broniewicz
 */
interface BookingRepository extends Repository<Booking, Long> {

    Option<Booking> findById(Long id);

    Option<Booking> findByBooker(Booker booker);
}
