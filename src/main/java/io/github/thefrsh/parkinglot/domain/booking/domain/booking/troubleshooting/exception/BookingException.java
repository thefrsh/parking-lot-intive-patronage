package io.github.thefrsh.parkinglot.domain.booking.domain.booking.troubleshooting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown in case of conflict when booking
 * @author Michal Broniewicz
 */
@ResponseStatus(code = HttpStatus.CONFLICT)
public class BookingException extends RuntimeException {

    public BookingException(String message) {

        super(message);
    }
}
