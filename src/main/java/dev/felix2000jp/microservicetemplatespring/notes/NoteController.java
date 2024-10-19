package dev.felix2000jp.microservicetemplatespring.notes;

import dev.felix2000jp.microservicetemplatespring.notes.dtos.CreateNoteDto;
import dev.felix2000jp.microservicetemplatespring.notes.dtos.NoteDto;
import dev.felix2000jp.microservicetemplatespring.notes.dtos.UpdateNoteDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteMapper noteMapper;
    private final NoteService noteService;

    public NoteController(NoteMapper noteMapper, NoteService noteService) {
        this.noteMapper = noteMapper;
        this.noteService = noteService;
    }

    @GetMapping("/{id}")
    ResponseEntity<NoteDto> getById(@PathVariable UUID id) {
        var note = noteService.findById(id);
        var body = noteMapper.toDto(note);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/title/{title}")
    ResponseEntity<NoteDto> getByTitle(@PathVariable String title) {
        var note = noteService.findByTitle(title);
        var body = noteMapper.toDto(note);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    ResponseEntity<Void> create(@Valid @RequestBody CreateNoteDto createNoteDto) {
        var noteToCreate = noteMapper.toEntity(createNoteDto);
        var note = noteService.create(noteToCreate);
        var location = URI.create("/api/notes/" + note.getId());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateNoteDto updateNoteDto) {
        var noteToUpdate = noteMapper.toEntity(id, updateNoteDto);
        noteService.update(noteToUpdate);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
