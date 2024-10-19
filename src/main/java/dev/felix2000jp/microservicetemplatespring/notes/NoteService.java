package dev.felix2000jp.microservicetemplatespring.notes;

import dev.felix2000jp.microservicetemplatespring.notes.exceptions.NoteNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    Note findById(UUID id) {
        return noteRepository
                .findById(id)
                .orElseThrow(NoteNotFoundException::new);
    }

    Note findByTitle(String title) {
        return noteRepository
                .findByTitle(title)
                .orElseThrow(NoteNotFoundException::new);
    }

    Note create(Note note) {
        return noteRepository.save(note);
    }

    Note update(Note note) {
        var noteToUpdate = noteRepository
                .findById(note.getId())
                .orElseThrow(NoteNotFoundException::new);

        noteToUpdate.setTitle(note.getTitle());
        noteToUpdate.setContent(note.getContent());
        return noteRepository.save(noteToUpdate);
    }

    Note delete(UUID id) {
        var noteToDelete = noteRepository
                .findById(id)
                .orElseThrow(NoteNotFoundException::new);

        noteRepository.delete(noteToDelete);
        return noteToDelete;
    }

}
