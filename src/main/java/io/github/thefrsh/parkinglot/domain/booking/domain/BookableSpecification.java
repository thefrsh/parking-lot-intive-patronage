package io.github.thefrsh.parkinglot.domain.booking.domain;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;
import io.github.thefrsh.parkinglot.domain.sharedkernel.Specification;
import io.github.thefrsh.parkinglot.domain.sharedkernel.annotation.DomainSpecification;

@DomainSpecification
class BookableSpecification implements Specification<ParkingSpot> {

    @Override
    public boolean isSatisfiedBy(ParkingSpot candidate) {

        return candidate.bookable();
    }
}
