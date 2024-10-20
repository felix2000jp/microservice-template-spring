package dev.felix2000jp.microservicetemplatespring.notes;

import dev.felix2000jp.microservicetemplatespring.appusers.Appuser;
import dev.felix2000jp.microservicetemplatespring.config.auth.AppuserPrincipal;
import dev.felix2000jp.microservicetemplatespring.exceptions.HttpNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
class NoteService {

    private final EntityManager entityManager;
    private final NoteMapper noteMapper;
    private final NoteRepository noteRepository;

    NoteService(EntityManager entityManager, NoteMapper noteMapper, NoteRepository noteRepository) {
        this.entityManager = entityManager;
        this.noteMapper = noteMapper;
        this.noteRepository = noteRepository;
    }

    public List<NoteDto> findAll(AppuserPrincipal principal) {
        var notes = noteRepository
                .findByAppuserId(principal.id());

        return notes.stream().map(noteMapper::toDto).toList();
    }

    NoteDto findById(AppuserPrincipal principal, UUID id) {
        var note = noteRepository
                .findByIdAndAppuserId(id, principal.id())
                .orElseThrow(HttpNotFoundException::new);

        return noteMapper.toDto(note);
    }

    NoteDto create(AppuserPrincipal principal, CreateNoteDto createNoteDto) {
        var newNote = new Note(
                createNoteDto.title(),
                createNoteDto.content(),
                entityManager.getReference(Appuser.class, principal.id())
        );
        var noteSaved = noteRepository.save(newNote);
        return noteMapper.toDto(noteSaved);
    }

    NoteDto update(AppuserPrincipal principal, UUID noteId, UpdateNoteDto updateNoteDto) {
        var noteToUpdate = noteRepository
                .findByIdAndAppuserId(noteId, principal.id())
                .orElseThrow(HttpNotFoundException::new);

        noteToUpdate.update(updateNoteDto.title(), updateNoteDto.content());
        var noteSaved = noteRepository.save(noteToUpdate);
        return noteMapper.toDto(noteSaved);
    }

    NoteDto delete(AppuserPrincipal principal, UUID id) {
        var noteToDelete = noteRepository
                .findByIdAndAppuserId(id, principal.id())
                .orElseThrow(HttpNotFoundException::new);

        noteRepository.delete(noteToDelete);
        return noteMapper.toDto(noteToDelete);
    }

}
