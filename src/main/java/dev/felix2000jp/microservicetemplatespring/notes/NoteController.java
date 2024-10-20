package dev.felix2000jp.microservicetemplatespring.notes;

import dev.felix2000jp.microservicetemplatespring.config.auth.AppuserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
class NoteController {

    private final NoteService noteService;

    NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    ResponseEntity<List<NoteDto>> findAll(@AuthenticationPrincipal AppuserPrincipal principal    ) {
        var body = noteService.findAll(principal);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    ResponseEntity<NoteDto> getById(
            @AuthenticationPrincipal AppuserPrincipal principal,
            @PathVariable UUID id
    ) {
        var body = noteService.findById(principal, id);
        return ResponseEntity.ok(body);
    }

    @PostMapping
    ResponseEntity<Void> create(
            @AuthenticationPrincipal AppuserPrincipal principal,
            @Valid @RequestBody CreateNoteDto createNoteDto
    ) {
        var body = noteService.create(principal, createNoteDto);
        var location = URI.create("/api/notes/" + body.id());
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> update(
            @AuthenticationPrincipal AppuserPrincipal principal,
            @PathVariable UUID id,
            @Valid @RequestBody UpdateNoteDto updateNoteDto
    ) {
        noteService.update(principal, id, updateNoteDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(
            @AuthenticationPrincipal AppuserPrincipal principal,
            @PathVariable UUID id
    ) {
        noteService.delete(principal, id);
        return ResponseEntity.noContent().build();
    }

}
