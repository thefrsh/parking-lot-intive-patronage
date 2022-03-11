package io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.Booker;
import io.github.thefrsh.parkinglot.infrastructure.persistence.Persistence;

public interface BookerPersistence extends Persistence<Booker, Long> {

}
