package io.github.thefrsh.parkinglot.domain.booking;

import io.github.thefrsh.dddcqrs.domain.Rule;
import io.github.thefrsh.dddcqrs.domain.annotation.DomainRule;
import io.github.thefrsh.parkinglot.domain.booking.model.ParkingSpot;

@DomainRule
class BookableRule implements Rule<ParkingSpot> {

    @Override
    public boolean meets(ParkingSpot candidate) {

        return candidate.bookable();
    }
}
