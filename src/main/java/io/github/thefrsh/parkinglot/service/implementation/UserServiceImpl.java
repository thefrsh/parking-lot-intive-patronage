package io.github.thefrsh.parkinglot.service.implementation;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.service.UserService;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public void createBooking(Long userId, Long spotId) {

        // to do
    }

    @Override
    public void deleteBooking(Long userId, Long spotId) {

        // to do
    }

    @Override
    public List<ParkingSpotResponse> getBookedSpots(Long userId) {

        return List.empty();
    }
}
