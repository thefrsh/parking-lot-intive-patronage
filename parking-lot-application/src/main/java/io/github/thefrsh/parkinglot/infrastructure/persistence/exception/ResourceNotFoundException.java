package io.github.thefrsh.parkinglot.infrastructure.persistence.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Thrown when no resource is found
 * @author Michal Broniewicz
 */
public class ResourceNotFoundException extends ResponseStatusException {

    public ResourceNotFoundException(String message) {

        super(HttpStatus.NOT_FOUND, message);
    }
}
