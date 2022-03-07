package io.github.thefrsh.parkinglot.domain.booking.domain.port.outgoing;

import io.github.thefrsh.parkinglot.domain.booking.domain.model.Booker;
import io.github.thefrsh.parkinglot.infrastructure.Persistence;

public interface BookerPersistence extends Persistence<Booker, Long> {

}
