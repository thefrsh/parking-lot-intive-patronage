package io.github.thefrsh.parkinglot.domain.booking.domain.port.outgoing;

import io.github.thefrsh.parkinglot.infrastructure.Persistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;
import io.vavr.collection.List;

public interface ParkingSpotPersistence extends Persistence<ParkingSpot, Long> {

    List<ParkingSpot> loadAll();
}
