package io.github.thefrsh.parkinglot.query.projection;

import io.github.thefrsh.parkinglot.infrastructure.model.ParkingSpot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@SuppressWarnings(value = "unused")
@Projection(name = "parkingSpotProjection", types = ParkingSpot.class)
public interface ParkingSpotProjection {

    @Value(value = "#{target.id}")
    long getId();

    int getNumber();

    int getStorey();

    boolean getDisability();
}
