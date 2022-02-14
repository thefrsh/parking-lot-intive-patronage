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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Performs domain operations on {@link User}
 * @author Michal Broniewicz
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ParkingSpotService parkingSpotService;
    private final ModelMapper modelMapper;

    /**
     * Books the parking spot defined by {@code spotId} for the user defined by {@code userId}
     *
     * @param  userId           {@link io.github.thefrsh.parkinglot.model.User} ID
     * @param  spotId           {@link io.github.thefrsh.parkinglot.model.ParkingSpot} ID
     * @throws BookingException if parking spot has been already booked
     * @see    Transactional
     */
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

    /**
     * Deletes the booking of parking spot defined by {@code spotId} for the user with ID {@code userId}
     *
     * @param  userId           {@link io.github.thefrsh.parkinglot.model.User} ID
     * @param  spotId           {@link io.github.thefrsh.parkinglot.model.ParkingSpot} ID
     * @throws BookingException if parking spot was not booked by the user
     * @see    Transactional
     */
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

    /**
     * Returns the list of all parking spots booked by user with ID {@code userId}
     * @param  userId              {@link io.github.thefrsh.parkinglot.model.User} ID
     * @return List of all booked spots by user defined by {@code userId}
     * @see    ParkingSpotResponse
     * @see    Transactional
     */
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
