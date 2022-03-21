package io.github.thefrsh.parkinglot.application.troubleshooting;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Getter
public class ErrorResponse {

    private final Date timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    @Builder
    public ErrorResponse(HttpStatus status, String message, String path) {

        this.timestamp = new Date();
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }
}
