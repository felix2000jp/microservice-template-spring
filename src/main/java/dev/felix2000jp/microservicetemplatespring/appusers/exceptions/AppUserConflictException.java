package dev.felix2000jp.microservicetemplatespring.appusers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AppUserConflictException extends ResponseStatusException {

    public AppUserConflictException() {
        super(HttpStatus.CONFLICT, "User already exists");
    }

}
