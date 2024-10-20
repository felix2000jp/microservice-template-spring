package dev.felix2000jp.microservicetemplatespring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class HttpNotFoundException extends ResponseStatusException {

    public HttpNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }

    public HttpNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
