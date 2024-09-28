package dev.felix2000jp.microservicetemplatespring.appusers.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateAppUserDto(
        @NotBlank
        @Size(min = 3, max = 50)
        String username,
        @NotBlank
        @Size(min = 3, max = 50)
        String password
) {
}
