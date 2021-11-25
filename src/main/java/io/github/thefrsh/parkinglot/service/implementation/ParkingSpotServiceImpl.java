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

/**
 * Performs domain operations on {@link ParkingSpot}
 * @author Michal Broniewicz
 */
@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {

    private final ParkingSpotRepository parkingSpotRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ParkingSpotServiceImpl(ParkingSpotRepository parkingSpotRepository, ModelMapper modelMapper) {

        this.parkingSpotRepository = parkingSpotRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns the list of parking spots depending on availability
     *
     * @param  available     if true - available parking spots are returned, booked ones otherwise
     * @return List of all available/not available parking spots
     * @see    Transactional
     */
    @Override
    @Transactional(readOnly = true)
    public List<ParkingSpotResponse> getParkingSpots(Boolean available) {

        return parkingSpotRepository.findAllByOwnerIsNullDependingOnAvailable(available)
                .map(parkingSpot -> modelMapper.map(parkingSpot, ParkingSpotResponse.class));
    }

    /**
     * Returns {@link ParkingSpot} object defined by ID {@code id}
     *
     * @param id {@link ParkingSpot} ID
     * @return   Parking Spot object
     * @throws   ResourceNotFoundException if no parking spot is found
     */
    @Override
    public ParkingSpot findParkingSpotById(Long id) {

        return parkingSpotRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                String.format("Parking spot with id %d is not found", id)
        ));
    }
}
