package dev.felix2000jp.microservicetemplatespring.notes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NoteConflictException extends ResponseStatusException {

    public NoteConflictException() {
        super(HttpStatus.CONFLICT, "Note already exists");
    }

}
