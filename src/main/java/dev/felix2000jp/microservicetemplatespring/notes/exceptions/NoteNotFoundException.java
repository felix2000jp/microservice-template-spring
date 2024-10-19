package dev.felix2000jp.microservicetemplatespring.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoteNotFoundException extends ResponseStatusException {

    public NoteNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Note was not found");
    }

}
