package io.github.thefrsh.parkinglot.troubleshooting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when no resource is found
 * @author Michal Broniewicz
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {

        super(message);
    }
}
