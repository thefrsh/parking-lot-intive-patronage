package io.github.thefrsh.parkinglot.service;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.model.ParkingSpot;
import io.vavr.collection.List;

public interface ParkingSpotService {

    List<ParkingSpotResponse> getParkingSpots(Boolean available);

    ParkingSpot findParkingSpotById(Long id);
}