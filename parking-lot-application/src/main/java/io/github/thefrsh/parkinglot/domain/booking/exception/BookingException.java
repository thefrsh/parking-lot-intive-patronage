package io.github.thefrsh.parkinglot.domain.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Thrown in case of conflict when booking
 * @author Michal Broniewicz
 */
public class BookingException extends ResponseStatusException {

    public BookingException(String message) {

        super(HttpStatus.CONFLICT, message);
    }
}
