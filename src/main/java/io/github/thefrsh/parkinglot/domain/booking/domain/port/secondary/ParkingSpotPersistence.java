package io.github.thefrsh.parkinglot.domain.booking.domain.port.secondary;

import io.github.thefrsh.parkinglot.infrastructure.persistence.Persistence;
import io.github.thefrsh.parkinglot.domain.booking.domain.model.ParkingSpot;
import io.vavr.collection.List;

public interface ParkingSpotPersistence extends Persistence<ParkingSpot, Long> {

    List<ParkingSpot> loadAll();
}
