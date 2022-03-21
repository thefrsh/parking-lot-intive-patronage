package io.github.thefrsh.parkinglot.domain.booking.port.secondary;

import io.github.thefrsh.dddcqrs.infrastructure.Persistence;
import io.github.thefrsh.parkinglot.domain.booking.model.Booker;

public interface BookerPersistence extends Persistence<Booker> {

}
