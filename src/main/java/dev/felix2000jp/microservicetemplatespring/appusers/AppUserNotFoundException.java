package dev.felix2000jp.microservicetemplatespring.appusers;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class AppUserNotFoundException extends ResponseStatusException {

    AppUserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User was not found");
    }

}
