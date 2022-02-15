package io.github.thefrsh.parkinglot.domain.booking.application.controller;

import io.github.thefrsh.parkinglot.domain.booking.domain.port.incoming.BookingCreator;
import io.github.thefrsh.parkinglot.domain.booking.domain.port.incoming.BookingDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class BookingController {

    private final BookingCreator bookingCreator;
    private final BookingDeleter bookingDeleter;

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @PostMapping(path = "/{userId}/booked-spots/{spotId}")
    public void createBooking(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long spotId) {

        bookingCreator.createBooking(userId, spotId);
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{userId}/booked-spots/{spotId}")
    public void deleteBooking(@Min(1) @PathVariable Long userId, @Min(1) @PathVariable Long spotId) {

        bookingDeleter.deleteBooking(userId, spotId);
    }
}
