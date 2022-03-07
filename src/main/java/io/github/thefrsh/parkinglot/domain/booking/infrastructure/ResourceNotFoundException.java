package io.github.thefrsh.parkinglot.domain.booking.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when no resource is found
 * @author Michal Broniewicz
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {

        super(message);
    }
}
