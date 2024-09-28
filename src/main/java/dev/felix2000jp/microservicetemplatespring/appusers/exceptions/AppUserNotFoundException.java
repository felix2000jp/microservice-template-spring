package dev.felix2000jp.microservicetemplatespring.appusers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AppUserNotFoundException extends ResponseStatusException {

    public AppUserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User was not found");
    }

}
