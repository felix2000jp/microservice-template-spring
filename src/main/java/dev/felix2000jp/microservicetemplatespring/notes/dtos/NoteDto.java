package dev.felix2000jp.microservicetemplatespring.notes.dtos;

import java.util.UUID;

public record NoteDto(UUID id, String title, String content) {
}
