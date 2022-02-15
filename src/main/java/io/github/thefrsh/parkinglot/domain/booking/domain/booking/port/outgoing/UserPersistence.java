package io.github.thefrsh.parkinglot.domain.booking.domain.booking.port.outgoing;

import io.github.thefrsh.parkinglot.infrastructure.Persistence;
import io.github.thefrsh.parkinglot.infrastructure.model.User;

public interface UserPersistence extends Persistence<User, Long> {

}
