package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.dddcqrs.domain.Specification;
import io.github.thefrsh.dddcqrs.domain.annotation.DomainSpecification;
import io.github.thefrsh.parkinglot.domain.booking.model.ParkingSpot;
import lombok.RequiredArgsConstructor;

@DomainSpecification
@RequiredArgsConstructor
class BookableSpecification implements Specification<ParkingSpot> {

    private final BookableRule bookableRule;

    @Override
    public boolean isSatisfiedBy(ParkingSpot candidate) {

        return bookableRule.meets(candidate);
    }
}
