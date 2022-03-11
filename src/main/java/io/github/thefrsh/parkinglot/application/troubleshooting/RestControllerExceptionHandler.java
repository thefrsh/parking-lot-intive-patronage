package io.github.thefrsh.parkinglot.application.troubleshooting;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Resolves exceptions that are not annotated with {@link ResponseStatus}
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
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolationException(HttpServletRequest request, Exception e) {

        var errors = new LinkedHashMap<String, Object>();
        errors.put("timestamp", new Date());
        errors.put("status", HttpStatus.BAD_REQUEST.value());
        errors.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        errors.put("message", e.getMessage());
        errors.put("path", request.getServletPath());

        return errors;
    }
}
