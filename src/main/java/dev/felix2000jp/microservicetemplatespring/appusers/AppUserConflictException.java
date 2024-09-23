package dev.felix2000jp.microservicetemplatespring.appusers;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class AppUserConflictException extends ResponseStatusException {

    AppUserConflictException() {
        super(HttpStatus.CONFLICT, "User already exists");
    }

}
