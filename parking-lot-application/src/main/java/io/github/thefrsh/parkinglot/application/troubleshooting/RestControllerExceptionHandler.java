package io.github.thefrsh.parkinglot.application.troubleshooting;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

/**
 * Resolves exceptions that do not extend {@link org.springframework.web.server.ResponseStatusException}
 * @see RestControllerAdvice
 *
 * @author Michal Broniewicz
 */
@RestControllerAdvice
class RestControllerExceptionHandler {

    /**
     * Resolves {@link ConstraintViolationException}
     *
     * @param request Actual instance of {@link HttpServletRequest}
     * @param e       Caught {@link ConstraintViolationException} class exception
     * @return        Error response based on {@link org.springframework.boot.web.reactive.error.DefaultErrorAttributes}
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(HttpServletRequest request, Exception e) {

        var errorEntity = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .path(request.getServletPath())
                .build();

        return ResponseEntity.badRequest().body(errorEntity);
    }
}
