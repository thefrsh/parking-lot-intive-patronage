package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.dddcqrs.domain.annotation.DomainRepository;
import io.github.thefrsh.parkinglot.domain.booking.model.Booker;
import io.github.thefrsh.parkinglot.domain.booking.model.Booking;
import io.github.thefrsh.parkinglot.domain.booking.port.secondary.BookingPersistence;
import io.github.thefrsh.parkinglot.infrastructure.persistence.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;

@DomainRepository
@RequiredArgsConstructor
class BookingPersistenceRepositoryAdapter implements BookingPersistence {

    private final BookingRepository bookingRepository;
    private final BookingFactory bookingFactory;

    @Override
    public Booking loadById(Long id) {

        var booking = bookingRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                "User with id %d is not found".formatted(id)
        ));

        bookingFactory.injectDependencies(booking);
        return booking;
    }

    @Override
    public Booking loadByBooker(Booker booker) {

        var booking = bookingRepository.findByBooker(booker)
                .getOrElse(() -> bookingFactory.createBooking(booker));

        bookingFactory.injectDependencies(booking);
        return booking;
    }
}
