package io.github.thefrsh.parkinglot.troubleshooting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class BookingException extends RuntimeException {

    public BookingException(String message) {

        super(message);
    }
}
