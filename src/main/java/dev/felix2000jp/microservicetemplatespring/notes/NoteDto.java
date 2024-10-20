package dev.felix2000jp.microservicetemplatespring.notes;

import java.util.UUID;

record NoteDto(UUID id, String title, String content) {
}
