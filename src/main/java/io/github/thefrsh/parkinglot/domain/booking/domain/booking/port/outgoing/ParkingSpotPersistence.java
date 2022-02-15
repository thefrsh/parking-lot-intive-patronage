package io.github.thefrsh.parkinglot.domain.booking.domain.booking.port.outgoing;

import io.github.thefrsh.parkinglot.infrastructure.Persistence;
import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;

public interface ParkingSpotPersistence extends Persistence<ParkingSpot, Long> {

}
