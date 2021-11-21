package io.github.thefrsh.parkinglot.service.implementation;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.model.User;
import io.github.thefrsh.parkinglot.model.repository.UserRepository;
import io.github.thefrsh.parkinglot.service.ParkingSpotService;
import io.github.thefrsh.parkinglot.service.UserService;
import io.github.thefrsh.parkinglot.troubleshooting.exception.BookingException;
import io.github.thefrsh.parkinglot.troubleshooting.exception.ResourceNotFoundException;
import io.vavr.collection.List;
import io.vavr.control.Option;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ParkingSpotService parkingSpotService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ParkingSpotService parkingSpotService,
                           ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.parkingSpotService = parkingSpotService;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void createBooking(Long userId, Long spotId) {

        var user = findUserById(userId);
        var parkingSpot = parkingSpotService.findParkingSpotById(spotId);

        Option.of(parkingSpot.getOwner())
                .peek(owner -> {
                    throw new BookingException(String.format("Parking spot %d has been already booked by user %d",
                            spotId, owner.getId()));
                })
                .onEmpty(() -> user.addParkingSpot(parkingSpot));
    }

    @Override
    @Transactional
    public void deleteBooking(Long userId, Long spotId) {

        var user = findUserById(userId);

        user.getBookedSpots()
                .find(spot -> spot.getId().equals(spotId))
                .peek(user::removeParkingSpot)
                .onEmpty(() -> {
                    throw new BookingException(String.format("Parking spot %d is not booked by user %d",
                            spotId, userId));
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<ParkingSpotResponse> getBookedSpots(Long userId) {

        var user = findUserById(userId);

        return user.getBookedSpots()
                .map(parkingSpot -> modelMapper.map(parkingSpot, ParkingSpotResponse.class));
    }

    private User findUserById(Long id) {

        return userRepository.findById(id).getOrElseThrow(() -> new ResourceNotFoundException(
                String.format("User with id %d is not found", id)
        ));
    }
}
