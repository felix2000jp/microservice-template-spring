package dev.felix2000jp.microservicetemplatespring.appusers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

record CreateAppUserDto(
        @NotBlank
        @Size(min = 3, max = 50)
        String username,

        @NotBlank
        @Size(min = 5, max = 150)
        String password
) {
}
