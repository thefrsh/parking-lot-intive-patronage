package io.github.thefrsh.parkinglot.service.implementation;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.service.ParkingSpotService;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    @Override
    public List<ParkingSpotResponse> getParkingSpots(Boolean available) {

        return List.empty();
    }
}
