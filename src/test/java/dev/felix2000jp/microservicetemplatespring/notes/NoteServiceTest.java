package dev.felix2000jp.microservicetemplatespring.notes;

import dev.felix2000jp.microservicetemplatespring.notes.exceptions.NoteNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    private Note note;

    @BeforeEach
    void setUp() {
        note = new Note(UUID.randomUUID(), "title", "content");
    }

    @Test
    void findById_should_return_note_when_note_exists() {
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        var actual = noteService.findById(note.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(note);
    }

    @Test
    void findById_should_throw_when_note_not_exists() {
        when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());

        var actual = catchThrowable(() -> noteService.findById(note.getId()));

        assertThat(actual).isInstanceOf(NoteNotFoundException.class);
    }

    @Test
    void findByTitle_should_return_note_when_note_exists() {
        when(noteRepository.findByTitle(note.getTitle())).thenReturn(Optional.of(note));

        var actual = noteService.findByTitle(note.getTitle());

        assertThat(actual).usingRecursiveComparison().isEqualTo(note);
    }

    @Test
    void findByTitle_should_throw_when_note_not_exists() {
        when(noteRepository.findByTitle(note.getTitle())).thenReturn(Optional.empty());

        var actual = catchThrowable(() -> noteService.findByTitle(note.getTitle()));

        assertThat(actual).isInstanceOf(NoteNotFoundException.class);
    }

    @Test
    void create_should_return_note() {
        var noteToCreate = new Note(note.getTitle(), note.getContent());

        when(noteRepository.save(noteToCreate)).thenReturn(note);

        var actual = noteService.create(noteToCreate);

        assertThat(actual).usingRecursiveComparison().isEqualTo(note);
    }

    @Test
    void update_should_return_note_when_note_exists() {
        var updatedNote = new Note(note.getId(), "new title", "new content");

        when(noteRepository.findById(updatedNote.getId())).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(updatedNote);

        var actual = noteService.update(updatedNote);

        assertThat(actual).usingRecursiveComparison().isEqualTo(updatedNote);
    }

    @Test
    void update_should_throw_when_note_not_exists() {
        var updatedNote = new Note(note.getId(), "new title", "new content");

        when(noteRepository.findById(updatedNote.getId())).thenReturn(Optional.empty());

        var actual = catchThrowable(() -> noteService.update(note));

        assertThat(actual).isInstanceOf(NoteNotFoundException.class);
    }

    @Test
    void delete_should_return_note_when_note_exists() {
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        var actual = noteService.delete(note.getId());

        assertThat(actual).usingRecursiveComparison().isEqualTo(note);
    }

    @Test
    void delete_should_throw_when_note_not_exists() {
        when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());

        var actual = catchThrowable(() -> noteService.delete(note.getId()));

        assertThat(actual).isInstanceOf(NoteNotFoundException.class);
    }

}