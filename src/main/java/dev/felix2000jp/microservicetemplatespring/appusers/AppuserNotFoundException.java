package dev.felix2000jp.microservicetemplatespring.appusers;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class AppuserNotFoundException extends ResponseStatusException {

    AppuserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User was not found");
    }

}
