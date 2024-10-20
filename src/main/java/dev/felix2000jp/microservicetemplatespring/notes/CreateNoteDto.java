package dev.felix2000jp.microservicetemplatespring.notes;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

record CreateNoteDto(
        @Size(min = 3, max = 50)
        @NotBlank
        String title,
        @Size(min = 3, max = 500)
        @NotBlank
        String content
) {
}
