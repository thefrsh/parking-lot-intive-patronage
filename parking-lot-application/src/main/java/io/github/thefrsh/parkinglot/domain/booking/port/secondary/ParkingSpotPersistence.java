package io.github.thefrsh.parkinglot.domain.booking.port.secondary;

import io.github.thefrsh.dddcqrs.infrastructure.Persistence;
import io.github.thefrsh.parkinglot.domain.booking.model.ParkingSpot;
import io.vavr.collection.List;

public interface ParkingSpotPersistence extends Persistence<ParkingSpot> {

    List<ParkingSpot> loadAll();

    ParkingSpot loadById(Long parkingSpotId);
}
