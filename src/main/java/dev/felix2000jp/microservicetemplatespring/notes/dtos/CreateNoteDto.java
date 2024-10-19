package dev.felix2000jp.microservicetemplatespring.notes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateNoteDto(
        @Size(min = 3, max = 50)
        @NotBlank
        String title,
        @Size(min = 3, max = 500)
        @NotBlank
        String content
) {
}
