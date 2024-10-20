package dev.felix2000jp.microservicetemplatespring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HttpConflictException extends ResponseStatusException {

    public HttpConflictException() {
        super(HttpStatus.CONFLICT);
    }

    public HttpConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

}
