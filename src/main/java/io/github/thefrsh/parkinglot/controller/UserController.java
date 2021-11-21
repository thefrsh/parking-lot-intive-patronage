package io.github.thefrsh.parkinglot.controller;

import io.github.thefrsh.parkinglot.dto.response.ParkingSpotResponse;
import io.github.thefrsh.parkinglot.service.UserService;
import io.vavr.collection.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PatchMapping(path = "/{userId}/booked-spots/{spotId}")
    public void createBooking(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long spotId) {

        userService.createBooking(userId, spotId);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{userId}/booked-spots/{spotId}")
    public void deleteBooking(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long spotId) {

        userService.deleteBooking(userId, spotId);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/{userId}/booked-spots", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ParkingSpotResponse> getBookedSpots(@Min(1) @PathVariable Long userId) {

        return userService.getBookedSpots(userId);
    }
}
