package io.github.thefrsh.parkinglot.service;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.vavr.collection.List;

public interface UserService {

    void createBooking(Long userId, Long spotId);

    void deleteBooking(Long userId, Long spotId);

    List<ParkingSpotResponse> getBookedSpots(Long userId);
}
