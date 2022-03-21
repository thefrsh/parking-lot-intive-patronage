package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.dddcqrs.domain.DomainEventPublisher;
import io.github.thefrsh.dddcqrs.domain.annotation.DomainFactory;
import io.github.thefrsh.parkinglot.domain.booking.model.Booker;
import io.github.thefrsh.parkinglot.domain.booking.model.Booking;
import lombok.RequiredArgsConstructor;

@DomainFactory
@RequiredArgsConstructor
class BookingFactory {

    private final DomainEventPublisher domainEventPublisher;

    public Booking createBooking(Booker booker) {

        return new Booking(booker);
    }

    public void injectDependencies(Booking booking) {

        booking.setDomainEventPublisher(domainEventPublisher);
    }
}
