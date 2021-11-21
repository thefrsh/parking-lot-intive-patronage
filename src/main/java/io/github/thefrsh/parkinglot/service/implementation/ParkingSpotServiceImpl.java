package io.github.thefrsh.parkinglot.service.implementation;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.model.ParkingSpot;
import io.github.thefrsh.parkinglot.model.repository.ParkingSpotRepository;
import io.github.thefrsh.parkinglot.service.ParkingSpotService;
import io.github.thefrsh.parkinglot.troubleshooting.exception.ResourceNotFoundException;
import io.vavr.collection.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ParkingSpotServiceImpl(ParkingSpotRepository parkingSpotRepository, ModelMapper modelMapper) {

        this.parkingSpotRepository = parkingSpotRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingSpotResponse> getParkingSpots(Boolean available) {

        return parkingSpotRepository.findAllByOwnerIsNullDependingOnAvailable(available)
                .map(parkingSpot -> modelMapper.map(parkingSpot, ParkingSpotResponse.class));
    }

    @Override
    public ParkingSpot findParkingSpotById(Long id) {

        return parkingSpotRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                String.format("Parking spot with id %d is not found", id)
        ));
    }
}
